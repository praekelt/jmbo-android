package praekelt.weblistingapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import praekelt.weblistingapp.cache.ImageCache;
import praekelt.weblistingapp.R;

/**
 * Created by altus on 2015/02/18.
 */
public class ImageLoader {

    private Map<ImageView, String> imageViews = Collections.synchronizedMap((new WeakHashMap<ImageView, String>()));
    private ExecutorService executorService;
    private ImageCache imageCache;
    private int default_id = R.drawable.circle;
    private String directory;

    private Context context;

    public ImageLoader(Context context) {
        imageCache = new ImageCache();
        imageCache.setMemoryCache();
        executorService= Executors.newFixedThreadPool(5);

        this.context = context;
    }

    public void displayImage(String url, ImageView view, String name, String directory) {
        imageViews.put(view, name);
        this.directory = directory;

        Bitmap bitmap = imageCache.getBitmapFromMemCache(name);

        if(url == null) {
            view.setImageBitmap(null);
            return;
        }
        if(bitmap != null) {
            view.setImageBitmap(bitmap);
        } else {
            queueImages(url, view, name);
        }
    }

    private void queueImages(String url, ImageView view, String name) {
        ImageToLoad il = new ImageToLoad(url, view, name);
        executorService.submit(new ImageLoad(il));
    }

    private class ImageToLoad {
        public String url;
        public ImageView view;
        public String name;

        public ImageToLoad(String url, ImageView view, String name) {
            this.url = url;
            this.view = view;
            this.name = name;
        }
    }

    public Bitmap downLoadFile(ImageToLoad imageToLoad) throws IOException {
        // Downloads and saves images to external storage

        // TODO Time out inputstream
        File downloadedFile = new File(directory, imageToLoad.name);

        if (!downloadedFile.exists()) {
            URL url = new URL(imageToLoad.url);
            InputStream inputStream = url.openStream();
            OutputStream outputStream = new FileOutputStream(downloadedFile);
            URLConnection conn = url.openConnection();
            Log.d("Connection Status", conn.toString());
            conn.connect();
            int contentLength = conn.getContentLength();
            byte[] buffer = new byte[contentLength];
            int length;

            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.close();
        }

        Bitmap image = imageCache.getBitmapFromMemCache(imageToLoad.name);

        if (image == null) {
            image = BitmapFactory.decodeFile(directory + "/" + imageToLoad.name);
            imageCache.addBitmapToMemoryCache(imageToLoad.name, image);
        }
        return image;
    }

    android.os.Handler handler = new android.os.Handler();
    class ImageLoad implements Runnable {

        ImageToLoad imageToLoad;

        ImageLoad(ImageToLoad imageToLoad) {
            this.imageToLoad = imageToLoad;
        }

        @Override
        public void run() {
                if(imageViewReused(imageToLoad))
                    return;
            try {
                Bitmap image = downLoadFile(imageToLoad);

                if(imageViewReused(imageToLoad))
                    return;

                ImageDisplayer imageDisplayer = new ImageDisplayer(image, imageToLoad);
                handler.post(imageDisplayer);
            } catch (IOException e) {

                // TODO DEFAULT IMAGE
                // Decode the Default drawable if the image url is erroneous or was unreachable
                Bitmap image = BitmapFactory.decodeResource(context.getResources(), default_id);
                ImageDisplayer imageDisplayer = new ImageDisplayer(image, imageToLoad);
                handler.post(imageDisplayer);
                e.printStackTrace();
            }
        }
    }

    class ImageDisplayer implements Runnable {
        Bitmap bitmap;
        ImageToLoad imageToLoad;

        public ImageDisplayer(Bitmap bitmap, ImageToLoad imageToLoad) {
            this.bitmap = bitmap;
            this.imageToLoad = imageToLoad;
        }

        public void run() {

            if(imageViewReused(imageToLoad))
                return;

            // Final catch if the link was fine but download failed or image was not found in storage
            if(bitmap!=null) {
                imageToLoad.view.setImageBitmap(bitmap);
            }else {
                //TODO DEFAULT IMAGE
                imageToLoad.view.setImageResource(default_id);
            }
        }
    }

    boolean imageViewReused(ImageToLoad imageToLoad) {
        String tag = imageViews.get(imageToLoad.view);

        if(tag==null || !tag.equals(imageToLoad.name))
            return true;
        return false;
    }
}
