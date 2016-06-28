package com.lzh.zhihu.model;

import android.util.Log;

import com.google.gson.Gson;
import com.lzh.zhihu.manager.OkHttpClientManager;
import com.lzh.zhihu.manager.ResultCallback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by lzh on 2016/6/15.
 */
public class NewsContentInteractorImpl implements  NewsContentInteractor{
    public Gson gson;

    public NewsContentInteractorImpl() {
        gson = new Gson();
    }



    public void webViewLoadUrl(final NewsContentListener listener, String id) {
        Log.d("CESHI",id+"NewsContentInteractorImpl");
        OkHttpClientManager.getInstance().getEnqueue(Constant.getNewsContent + id, new ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                listener.onLoadNewsContentFailed();
            }

            @Override
            public void onResponse(Response response) {
                try {
                    listener.onLoadNewsContentSuccess(gson.fromJson(response.body().string(), NewsContent.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void getExtraData(final ExtraListener listener, String id) {
        OkHttpClientManager.getInstance().getEnqueue(Constant.getExtra + id, new ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                listener.onGerExtraFailed();
            }

            @Override
            public void onResponse(Response response) {
                try {
                    listener.onGetExtraSuccess(gson.fromJson(response.body().string(),ExtraInfo.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
