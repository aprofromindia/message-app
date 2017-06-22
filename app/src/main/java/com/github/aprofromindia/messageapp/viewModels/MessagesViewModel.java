package com.github.aprofromindia.messageapp.viewModels;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.github.aprofromindia.messageapp.entities.Message;
import com.github.aprofromindia.messageapp.http.WireService;
import com.github.aprofromindia.messageapp.utils.Supplier;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import lombok.Getter;

/**
 * Created by Apro on 17-06-2017.
 */

public final class MessagesViewModel {

    private static final int MAX_PAGE_NUM = 3;
    public static final int PAGE_SIZE = 50;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Getter
    private List<Message> messages = new ArrayList<>();
    private WireService service;
    @Getter
    private int reqPageNum = -1;
    private Scheduler mainScheduler;

    public MessagesViewModel(@NonNull WireService service, @NonNull Scheduler scheduler) {
        this.service = service;
        this.mainScheduler = scheduler;
    }

    public void fetchMessages(@IntRange(from = 0) final int index,
                              @NonNull final Supplier supplier) {
        if (index > reqPageNum * PAGE_SIZE && reqPageNum < MAX_PAGE_NUM) {
            reqPageNum = Math.min(MAX_PAGE_NUM, ++reqPageNum);

            disposable.add(service.getMessages(reqPageNum)
                    .observeOn(mainScheduler)
                    .subscribe(messages -> {
                        this.messages.addAll(messages);
                        supplier.apply();
                    }, throwable -> reqPageNum--));
        }
    }

    public void deleteMessage(@IntRange(from = 0) int index,
                              @NonNull final Supplier supplier) {
        if (index < messages.size()) {
            messages.remove(index);
            supplier.apply();
        }
    }

    void deInit() {
        disposable.dispose();
    }
}
