package com.github.aprofromindia.messageapp.viewModels;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.aprofromindia.messageapp.ui.MainActivityProvider;

/**
 * Created by Apro on 15-06-2017.
 */

public final class MessagesRetainFragment extends Fragment {

    public static final String TAG = MessagesRetainFragment.class.getSimpleName();

    private MessagesViewModel viewModel;

    public MessagesRetainFragment() {
    }

    public static MessagesRetainFragment newInstance(@NonNull Context context) {
        MessagesRetainFragment fragment = new MessagesRetainFragment();
        fragment.viewModel = new MainActivityProvider().provideViewModel(context);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.deInit();
    }

    public MessagesViewModel getViewModel() {
        return viewModel;
    }
}
