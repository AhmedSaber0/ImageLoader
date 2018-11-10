package com.application.android.imageloader.data.service;

import android.content.Context;
import android.support.annotation.NonNull;

import com.application.android.imageloader.util.NetworkHelper;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://pastebin.com/raw/";
    private static Retrofit retrofit = null;

    private ApiClient() {
    }

    public static Retrofit getRetrofitInstance(final Context context) {
        if (retrofit == null) {
            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    if (NetworkHelper.getInstance().isNetworkAvailable(context)) {
                        int maxAge = 60; // read from cache for 1 minute
                        return originalResponse.newBuilder()
                                .header("Cache-Control", "public, max-age=" + maxAge)
                                .build();
                    } else {
                        int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                        return originalResponse.newBuilder()
                                .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                .build();
                    }
                }
            };

            OkHttpClient okHttpClient = getOkHttpClient(context, interceptor);

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    @NonNull
    private static OkHttpClient getOkHttpClient(Context context, Interceptor interceptor) {
        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(context.getCacheDir(), cacheSize);
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(interceptor)
                .build();
    }
}
