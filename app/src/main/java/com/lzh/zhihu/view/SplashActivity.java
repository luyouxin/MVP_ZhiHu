package com.lzh.zhihu.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzh.zhihu.R;
import com.lzh.zhihu.model.StartImage;
import com.lzh.zhihu.presenter.SplashPresenter;

/**
 * Created by lzh on 2016/6/8.
 */
public class SplashActivity extends Activity implements SplashView {
    private ImageView imageView;
    private SplashPresenter splashPresenter = new SplashPresenter(this);
    private static final int LOADIMAGE = 0x01;
    private static final int TOMAINACTIVITY = 0x02;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOADIMAGE:
                    Glide.with(SplashActivity.this)
                            .load((String) msg.obj)
                            .into(imageView);
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initViews();
    }

    private void initViews() {
        imageView = (ImageView) findViewById(R.id.imageview);
        splashPresenter.loadImage(this, imageView);
    }

    @Override
    public void loadImageView(StartImage startImage) {
        Message message = mHandler.obtainMessage();
        message.what = LOADIMAGE;
        message.obj = startImage.getImg();
        mHandler.sendMessage(message);
    }

    @Override
    public void toMainActivity() {
        Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
