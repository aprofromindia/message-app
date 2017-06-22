package com.github.aprofromindia.messageapp.entities;

import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Created by Apro on 15-06-2017.
 */

@Value
@EqualsAndHashCode(of = "id")
public final class Message {
    private String id;
    private long time;
    private String text;
}
