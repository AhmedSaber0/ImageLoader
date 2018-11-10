package com.application.android.imageloader.data.source;

import com.application.android.imageloader.data.model.Feed;

import java.util.List;

public abstract class DataSource {

    public DataSource() {
    }

    public abstract void getPhotos(int page, GetPhotosCallback callback);

    public interface GetPhotosCallback {
        void onSuccess(List<Feed> photos);

        void onFailure(Throwable throwable);

        void onNetworkFailure();

    }
}
