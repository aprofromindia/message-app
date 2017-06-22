package com.github.aprofromindia.messageapp;

import com.github.aprofromindia.messageapp.entities.Message;
import com.github.aprofromindia.messageapp.http.RestClient;
import com.github.aprofromindia.messageapp.http.WireService;

import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.observers.TestObserver;

/**
 * Created by Apro on 22-06-2017.
 */

public class WireServiceTest {

    private WireService service = new RestClient().createService(WireService.class, new File(""));

    @Test
    public void test_messages_count() {
        // given
        TestObserver<List<Message>> testObserver = new TestObserver<>();

        // when
        service.getMessages(0)
                .subscribe(testObserver);

        // then
        testObserver.assertEmpty();
        testObserver.assertSubscribed();

        testObserver.awaitTerminalEvent(10, TimeUnit.SECONDS);
        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);
        testObserver.assertNoTimeout();
        testObserver.assertValue(messages -> !messages.isEmpty() && messages.size() == 50);
    }
}
