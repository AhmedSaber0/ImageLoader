package com.application.android.imageloader.data.service;

import com.application.android.imageloader.data.model.Feed;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    @GET("wgkJgazE/")
    Observable<List<Feed>> getFeedData(/*@Query("page") int query*/);
}

