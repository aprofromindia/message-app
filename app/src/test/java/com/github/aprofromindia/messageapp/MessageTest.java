package com.github.aprofromindia.messageapp;

import com.github.aprofromindia.messageapp.entities.Message;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Apro on 22-06-2017.
 */

public class MessageTest {

    private Message message = new Message("1", 1, "sample");

    @Test
    public void id_equality() {
        assertEquals(new Message("1", 2, "sample2"), message);
        assertNotEquals(new Message("2", 1, "sample"), message);
    }

    @Test
    public void test_getter() {
        assertEquals("1", message.getId());
        assertEquals(1, message.getTime());
        assertEquals("sample", message.getText());
    }
}
