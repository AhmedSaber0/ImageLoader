package com.application.android.imageloader.download;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.application.android.imageloader.util.cache.BitmapLruCache;

public class ImageLoader {

    private volatile static ImageLoader instance;
    private BitmapLruCache bitmapLruCache;

    private ImageLoader(BitmapLruCache bitmapLruCache) {
        this.bitmapLruCache = bitmapLruCache;
    }

    /**
     * Returns singleton class instance
     */
    public static ImageLoader getInstance(BitmapLruCache bitmapLruCache) {
        if (instance == null) {
            instance = new ImageLoader(bitmapLruCache);
        }
        return instance;
    }

    public void downloadImage(String url, final ImageView imageView) {
        Bitmap image = bitmapLruCache.get(url);
        if (image != null) {
            Log.w("data loaded from", "cache");
            imageView.setImageBitmap(image);
        } else {
            Log.w("data loaded from", "remote");
            new DownloadImageTask(bitmapLruCache, new DownloadImageTask.downloadImageCallback() {
                @Override
                public void onImageDownloaded(Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                }

                @Override
                public void onImageDownloadError() {
                }
            }).download(url);
        }
    }

}
