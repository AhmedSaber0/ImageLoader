package com.application.android.imageloader.util.cache;

import android.graphics.Bitmap;

public class BitmapLruCache extends LruCache<String, Bitmap> {

    private static BitmapLruCache instance;

    /**
     * Returns the number of bytes in {@link android.graphics.Bitmap}.
     *
     * @param bitmap bitmap
     * @return the number of bytes.
     */
    private static int getBitmapSize(Bitmap bitmap) {
        int bytes = getByteCount(bitmap);
        if (bytes < 0) {
            throw new IllegalStateException("Bitmap size is negative. Size=" + bitmap);
        }
        return bytes;
    }

    private static int getByteCount(Bitmap bitmap) {
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    public static BitmapLruCache getInstance() {
        if (instance == null) {
            instance = new BitmapLruCache();
        }
        return instance;
    }

    @Override
    protected String getClassName() {
        return BitmapLruCache.class.getName();
    }

    @Override
    protected int getValueSize(Bitmap value) {
        return getBitmapSize(value);
    }

}
