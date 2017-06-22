package com.github.aprofromindia.messageapp.ui;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.aprofromindia.messageapp.AppProvider;
import com.github.aprofromindia.messageapp.viewModels.MessagesViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Apro on 20-06-2017.
 */

public class MainActivityProvider {

    public MessagesViewModel provideViewModel(@NonNull final Context context) {
        return new MessagesViewModel(AppProvider.provideWireService(context),
                AndroidSchedulers.mainThread());
    }
}
