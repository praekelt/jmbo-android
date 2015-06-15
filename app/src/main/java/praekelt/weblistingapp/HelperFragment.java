package praekelt.weblistingapp;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MIME;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import praekelt.weblistingapp.Utils.SavedData;
import praekelt.weblistingapp.Utils.fileSystemUtils;

/**
 * Created by altus on 2015/02/13.
 * Headless Fragment to delete old files
 * and retain data between state changes
 */
public class HelperFragment extends Fragment {
    public helperCallbacks callback;
    private SavedData savedData;
    //private final String recordPostURL = "http://192.168.0.3:8000/v1/api/upload-audio-file/";
    private final String recordPostURL = "http://qa-visual-radio.za.prk-host.net/v1/api/upload-audio-file/";
    private ExecutorService executorService;

    /**
     *
     * @param activity
     */
    public void onAttach(Activity activity) {
        executorService= Executors.newFixedThreadPool(5);
        super.onAttach(activity);
        try {
            callback = (helperCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }

    public interface helperCallbacks {
        public void makeToast(String message);
        // colour 0 = green, 1 = red
        public void postNotification(int colour, String message, String operation);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        FileSystemPoll fl = new FileSystemPoll();
        fl.start();
    }

    public void onDetach() {
        super.onDetach();
    }

    public void setData(SavedData savedData) {
        this.savedData = savedData;
    }

    public SavedData getData(){
        return savedData;
    }

    /**
     * Polls the image directory and deletes old files
     */
    class FileSystemPoll extends Thread {
        File directory = new File((String.valueOf(getActivity().getApplication().getExternalFilesDir(null)))+"/images");

        public void run() {

            while(!Thread.currentThread().isInterrupted()) {
                Date current = new Date();
                current.getTime();
                iterate(directory, current.getTime());
                try {
                    Thread.sleep(3600000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void iterate (File dir, Long current) {
            if (dir.exists()) {
                File[] files = dir.listFiles();
                for (int i = 0; i < files.length; ++i) {
                    Log.d("File time", String.valueOf(files[i].lastModified()));
                    if(files[i].lastModified() < (current - 3600000)) {
                        Log.i("File to Delete", files[i].getName());
                        files[i].delete();
                        Log.i("File Delted", "index Number" + i);
                    }
                }
            }
        }
    }
    public void sendPost(String directory, String fileName) {
        Log.i("Queued for Upload: ", ("Recording_" + fileName));
            executorService.submit(new sendCallin(directory, fileName));
    }

    Handler recordHandler = new Handler() {
        public void handleMessage(Message msg) {
        switch (msg.what) {
            case 0:
                callback.makeToast("No Recording Found");
                break;
            case 1:
                callback.postNotification(0, "Recording Sent", "new");
                break;
            case 2:
                callback.postNotification(1, "Recording Not Sent", "new");
                break;
        }
        }
    };

    class sendCallin implements Runnable {
        private String filePath = "";
        private String fileName = "";

        sendCallin(String directory, String fileName) {
            this.fileName = fileName;
            filePath = (directory + "" +  fileName);
        }

        @Override
        public void run() {
            File recording = new File(filePath);
            boolean exists = recording.exists();

            Log.d("Exists: ", String.valueOf(exists));

             if(!recording.exists()) {

                 Log.d("Missing", "File");
                 recordHandler.sendEmptyMessage(0);
                 return;
             }

            HttpClient client = new DefaultHttpClient();
            client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

            HttpPost post = new HttpPost(recordPostURL);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(MIME.UTF8_CHARSET);

            builder.addBinaryBody("file", recording, ContentType.DEFAULT_BINARY, (fileName + ".aac"));

            post.setEntity(builder.build());

            HttpResponse response = null;

            try {
                response = client.execute(post);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Log.d("RESPONSE ENTITY: ", EntityUtils.toString(response.getEntity()));

            if(response.getStatusLine().getStatusCode() == 201) {
                String md5 = "";
                JSONObject jb = null;
                JSONTokener tokener = null;
                String md5Return = "";
                try {
                    md5 = fileSystemUtils.fileMD5CheckSum(filePath);
                    Log.d("App", md5);

                    tokener = new JSONTokener(EntityUtils.toString(response.getEntity()));
                    jb = new JSONObject(tokener);
                    Log.d("JSON", jb.toString());

                    md5Return = jb.getString("md5");
                    Log.d("Response", md5Return);
                } catch (JSONException | IOException | NoSuchAlgorithmException e) {
                    Log.getStackTraceString(e);
                }

                if(md5Return.equals(md5)) {
                    Log.i("Recording Status: ", "Sent Successfull");
                    File delete = new File(filePath);
                    delete.delete();
                    recordHandler.sendEmptyMessage(1);
                } else {
                    recordHandler.sendEmptyMessage(2);
                }
            }
            client.getConnectionManager().shutdown();

        }
    }
}
