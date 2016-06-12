package com.lzh.zhihu.model;

import com.google.gson.Gson;
import com.lzh.zhihu.manager.OkHttpClientManager;
import com.lzh.zhihu.manager.ResultCallback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by lzh on 2016/6/8.
 */
public class SplashInteractorImpl implements SplashInteractor {
    public Gson gson;

    public SplashInteractorImpl() {
        gson = new Gson();
    }

    @Override
    public void loadImage(final SplashListener listener) {
        OkHttpClientManager.getInstance().getEnqueue(Constant.startImageUrl, new ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                listener.onSplashFailed();
            }

            @Override
            public void onResponse(Response response) {
                try {
                    listener.onSplashSuccess(gson.fromJson(response.body().string(), StartImage.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
