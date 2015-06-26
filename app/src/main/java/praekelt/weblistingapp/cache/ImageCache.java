package praekelt.weblistingapp.cache;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by altus on 2015/01/26.
 */
public class ImageCache extends Application{
    private LruCache<String, Bitmap> mMemoryCache;

    public void setMemoryCache() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        try {
            return mMemoryCache.get(key);
        } catch (NullPointerException e) {
            return null;
        }
    }
}
