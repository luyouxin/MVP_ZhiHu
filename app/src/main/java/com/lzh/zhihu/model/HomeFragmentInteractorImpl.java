package com.lzh.zhihu.model;

import com.google.gson.Gson;
import com.lzh.zhihu.manager.OkHttpClientManager;
import com.lzh.zhihu.manager.ResultCallback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by lzh on 2016/6/12.
 */
public class HomeFragmentInteractorImpl implements GetNewsInteractor {
    private Gson gson;

    public HomeFragmentInteractorImpl() {
        gson = new Gson();
    }

    @Override
    public void getCurrentDayNews(final GetCurrentDayNewsListener listener) {
        OkHttpClientManager.getInstance().getEnqueue(Constant.getCurrentsDayNews, new ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                listener.onGetCurrentDayNewsFailed();
            }

            @Override
            public void onResponse(Response response) {
                try {
                    listener.onGetCurrentDayNewsSuccess(gson.fromJson(response.body().string(), CurrentDayNews.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void getHistoryNews(final GetHistoryNewsListener listener, String date) {
        OkHttpClientManager.getInstance().getEnqueue(Constant.getHistoryNews + date, new ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                listener.onGetHistoryNewsFailed();
            }

            @Override
            public void onResponse(Response response) {
                try {
                    listener.onGetHistoryNewsSuccess(gson.fromJson(response.body().string(), HistoryNews.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
