package com.github.aprofromindia.messageapp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.aprofromindia.messageapp.http.RestClient;
import com.github.aprofromindia.messageapp.http.WireService;

import java.io.File;

/**
 * Created by Apro on 16-06-2017.
 */

public abstract class AppProvider {
    private static final RestClient restClient = new RestClient();

    private static File provideCacheDir(@NonNull final Context context) {
        return context.getCacheDir();
    }

    public static WireService provideWireService(@NonNull Context context) {
        return restClient.createService(WireService.class, provideCacheDir(context));
    }
}
