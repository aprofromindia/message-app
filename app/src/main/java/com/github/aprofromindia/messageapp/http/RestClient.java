package com.github.aprofromindia.messageapp.http;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Apro on 15-06-2017.
 */

public class RestClient {
    private static final String BASE_URL = "https://rawgit.com";

    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private Retrofit.Builder retroBuilder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory
                    .createWithScheduler(Schedulers.io()));

    public <S> S createService(Class<S> serviceClass, File cacheDir) {
        Retrofit retrofit = retroBuilder.client(httpClient
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .cache(new Cache(cacheDir, 10 * 1024))
                .build()).build();
        return retrofit.create(serviceClass);
    }
}
