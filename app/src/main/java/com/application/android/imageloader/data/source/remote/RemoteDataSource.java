package com.application.android.imageloader.data.source.remote;

import com.application.android.imageloader.data.model.Feed;
import com.application.android.imageloader.data.service.ApiService;
import com.application.android.imageloader.data.source.DataSource;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RemoteDataSource extends DataSource {

    private static RemoteDataSource remoteDataSource;

    private ApiService apiService;

    private RemoteDataSource(ApiService apiService) {
        super();
        this.apiService = apiService;

    }

    public static synchronized RemoteDataSource getInstance(ApiService apiService) {
        if (remoteDataSource == null) {

            remoteDataSource = new RemoteDataSource(apiService);
        }
        return remoteDataSource;
    }

    @Override
    public void getPhotos(int page, final GetPhotosCallback callback) {
        apiService.getFeedData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Feed>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Feed> feeds) {
                        callback.onSuccess(feeds);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });


//        retrofit2.Call<List<Feed>> call = apiService.getFeedData(/*page*/);
//        call.enqueue(
//                new retrofit2.Callback<List<Feed>>() {
//                    @Override
//                    public void onResponse(
//                            @NonNull retrofit2.Call<List<Feed>> call,
//                            @NonNull retrofit2.Response<List<Feed>> response) {
//                        if (response.isSuccessful()) {
//                            List<Feed> photoResponse = response.body();
//                            callback.onSuccess(photoResponse);
//                        } else {
//                            callback.onFailure(new Throwable());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(
//                            @NonNull retrofit2.Call<List<Feed>> call,
//                            @NonNull Throwable t) {
//                        callback.onFailure(t);
//                    }
//                });
    }
}
