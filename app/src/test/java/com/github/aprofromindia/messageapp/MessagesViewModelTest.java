package com.github.aprofromindia.messageapp;

import com.github.aprofromindia.messageapp.entities.Message;
import com.github.aprofromindia.messageapp.http.WireService;
import com.github.aprofromindia.messageapp.viewModels.MessagesViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Apro on 22-06-2017.
 */

public class MessagesViewModelTest {

    @Mock
    WireService mockService;
    private MessagesViewModel viewModel;
    private TestScheduler scheduler = new TestScheduler();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        when(mockService.getMessages(0))
                .thenReturn(Observable.just(Arrays.asList(new Message("1", 1, "sample"))));

        viewModel = new MessagesViewModel(mockService, scheduler);
    }

    @Test
    public void test_fetch_messages() throws InterruptedException {
        // given
        viewModel.fetchMessages(0, () -> {
        });

        // when
        scheduler.advanceTimeBy(10, TimeUnit.SECONDS);

        // then
        assertEquals(1, viewModel.getMessages().size());
    }

    @Test
    public void test_delete_message() {
        // given
        viewModel.fetchMessages(0, () -> {});
        scheduler.advanceTimeBy(10, TimeUnit.SECONDS);
        assertEquals(1, viewModel.getMessages().size());

        // when
        viewModel.deleteMessage(0, () -> {
        });

        // then
        assertEquals(0, viewModel.getMessages().size());
    }
}
