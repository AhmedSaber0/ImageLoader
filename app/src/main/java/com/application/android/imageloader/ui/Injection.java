package com.application.android.imageloader.ui;


import com.application.android.imageloader.data.DataRepository;
import com.application.android.imageloader.data.service.ApiService;
import com.application.android.imageloader.data.source.remote.RemoteDataSource;
import com.application.android.imageloader.util.NetworkHelper;

public class Injection {

    public static DataRepository provideDataRepository(ApiService apiService) {


        return DataRepository.getInstance(
                RemoteDataSource.getInstance(apiService),
                NetworkHelper.getInstance());
    }
}
