package com.lzh.zhihu.presenter;

import android.content.Context;
import android.os.Handler;
import android.widget.ImageView;

import com.lzh.zhihu.model.SplashInteractorImpl;
import com.lzh.zhihu.model.SplashListener;
import com.lzh.zhihu.model.StartImage;
import com.lzh.zhihu.view.SplashView;

/**
 * Created by lzh on 2016/6/8.
 */
public class SplashPresenter {
    private SplashInteractorImpl splashInteractorImlp;
    private SplashView splashView;
    private Handler mHandler = new Handler();

    public SplashPresenter(SplashView splashView) {
        this.splashView = splashView;
        this.splashInteractorImlp = new SplashInteractorImpl();
    }

    public void loadImage(final Context context, final ImageView imageView) {
        splashInteractorImlp.loadImage(new SplashListener() {
            @Override
            public void onSplashSuccess(final StartImage startImage) {
                splashView.loadImageView(startImage);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        splashView.toMainActivity();
                    }
                }, 2000);

            }

            @Override
            public void onSplashFailed() {
                 splashView.toMainActivity();
            }
        });
    }
}
