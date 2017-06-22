package com.github.aprofromindia.messageapp.ui.views;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.aprofromindia.messageapp.R;
import com.github.aprofromindia.messageapp.entities.Message;

/**
 * Created by Apro on 19-06-2017.
 */

public final class MessageView extends FrameLayout {

    private TextView textView;
    private ImageView imageView;

    public MessageView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public MessageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MessageView(@NonNull Context context, @Nullable AttributeSet attrs,
                       @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(@NonNull final Context context) {
        inflate(context, R.layout.view_message, this);

        textView = (TextView) findViewById(R.id.text_view);
        imageView = (ImageView) findViewById(R.id.image_view);
    }

    public void setMessage(@NonNull final Message message) {
        if (message.getText().startsWith("http://") || message.getText().startsWith("https://")) {
            Glide.with(imageView.getContext().getApplicationContext())
                    .load(message.getText())
                    .into(imageView);

            imageView.setVisibility(VISIBLE);
            textView.setVisibility(GONE);
        } else {
            textView.setText(message.getText());
            textView.setVisibility(VISIBLE);
            imageView.setVisibility(GONE);
        }
    }
}
