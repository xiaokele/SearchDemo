package com.example.administrator.test;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

import java.net.URL;

/**
 * Created by Administrator on 2016/11/15.
 */
public class DownImage {
    public String image_path;

    public DownImage(String image_path) {
        this.image_path = image_path;
    }

    public void loadImage(final ImageCallBack callBack) {

        final Handler handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Drawable drawable = (Drawable) msg.obj;
                callBack.getDrawable(drawable);
            }

        };

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Drawable drawable = Drawable.createFromStream(new URL(
                            image_path).openStream(), "");

                    Message message = Message.obtain();
                    message.obj = drawable;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public interface ImageCallBack {
        void getDrawable(Drawable drawable);
    }
}
