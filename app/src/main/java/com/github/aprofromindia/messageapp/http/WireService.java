package com.github.aprofromindia.messageapp.http;

import com.github.aprofromindia.messageapp.entities.Message;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Apro on 18-06-2017.
 */

public interface WireService {

    @GET("/wireapp/android_test_app/master/endpoint/{pageNum}.json")
    Observable<List<Message>> getMessages(@Path("pageNum") int pageNum);
}
