package com.application.android.imageloader.download;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.application.android.imageloader.util.Utils;
import com.application.android.imageloader.util.cache.BitmapLruCache;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    private static final String TAG = DownloadImageTask.class.getName();

    /**
     * The client of this task that is waiting for the image to be
     * obtained.
     */
    private downloadImageCallback listener;
    private BitmapLruCache bitmapLruCache;

    /**
     * Constructs a new DownloadImageTask with a provided callback
     * listener.
     *
     * @param listener the client that is listening for a callback when
     *                 the image becomes available.
     */
    DownloadImageTask(BitmapLruCache bitmapLruCache, final downloadImageCallback listener) {
        this.listener = listener;
        this.bitmapLruCache = bitmapLruCache;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        final String url = urls[0];
        Bitmap bitmap = null;

        try {
            final InputStream inputStream = new URL(url).openStream();
            bitmap = getBitmap(inputStream);
            if (bitmap != null)
                bitmapLruCache.put(url, bitmap);
        } catch (final MalformedURLException malformedUrlException) {
            Log.e(TAG, "Failed to download malformed URL " + url
                    + malformedUrlException.getMessage());
        } catch (final IOException ioException) {
            Log.e(TAG, "Failed to download URL " + url
                    + ioException.getMessage());
        }

        return bitmap;
    }

    private Bitmap getBitmap(InputStream inputStream) {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 32 * 1024);
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            bufferedInputStream.mark(32 * 1024);
            BitmapFactory.decodeStream(bufferedInputStream, null, options);
            bufferedInputStream.reset();

            options.inSampleSize = Utils.calculateInSampleSize(options, 512, 512);
            options.inJustDecodeBounds = false;

            return BitmapFactory.decodeStream(bufferedInputStream, null, options);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedInputStream.close();
            } catch (IOException ignored) {
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap downloadedBitmap) {
        if (null != downloadedBitmap) {
            listener.onImageDownloaded(downloadedBitmap);
        } else {
            listener.onImageDownloadError();
        }
    }

    /**
     * Downloads image resources from a list of URLs concurrently.
     *
     * @param urls URLs to the images to download
     */
    void download(final String... urls) {
        executeOnExecutor(THREAD_POOL_EXECUTOR, urls);
    }


    public interface downloadImageCallback {
        /**
         * Called when the DownloadImageTask has successfully downloaded
         * an image.
         *
         * @param bitmap the image that has been downloaded.
         */
        void onImageDownloaded(final Bitmap bitmap);

        void onImageDownloadError();
    }
}
