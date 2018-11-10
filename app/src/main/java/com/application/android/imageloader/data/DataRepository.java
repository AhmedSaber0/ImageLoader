package com.application.android.imageloader.data;

import android.content.Context;

import com.application.android.imageloader.data.model.Feed;
import com.application.android.imageloader.data.source.DataSource;
import com.application.android.imageloader.util.NetworkHelper;

import java.util.List;

public class DataRepository {

    private static DataRepository dataRepository;
    private DataSource remoteDataSource;
    private NetworkHelper networkHelper;

    private DataRepository(DataSource remoteDataSource,
                           NetworkHelper networkHelper) {
        this.remoteDataSource = remoteDataSource;
        this.networkHelper = networkHelper;
    }

    public static synchronized DataRepository getInstance(DataSource remoteDataSource,
                                                          NetworkHelper networkHelper) {
        if (dataRepository == null) {
            dataRepository = new DataRepository(remoteDataSource, networkHelper);
        }
        return dataRepository;
    }

    public void getPhotos(Context context, int page, final DataSource.GetPhotosCallback callback) {
        remoteDataSource.getPhotos(page, new DataSource.GetPhotosCallback() {
            @Override
            public void onSuccess(List<Feed> photos) {
                callback.onSuccess(photos);
            }

            @Override
            public void onFailure(Throwable throwable) {
                callback.onFailure(throwable);
            }

            @Override
            public void onNetworkFailure() {
                callback.onNetworkFailure();
            }
        });
    }

    public void destroyInstance() {
        dataRepository = null;
    }
}
