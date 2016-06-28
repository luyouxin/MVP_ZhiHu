package com.lzh.zhihu.model;

import com.google.gson.Gson;
import com.lzh.zhihu.manager.OkHttpClientManager;
import com.lzh.zhihu.manager.ResultCallback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by lzh on 2016/6/24.
 */
public class MainDrawerInteractorImpl implements MainDrawerInteractor {
    private Gson gson;

    public MainDrawerInteractorImpl() {
        gson = new Gson();
    }

    @Override
    public void initDrawerData(final ThemesListener listener) {
        OkHttpClientManager.getInstance().getEnqueue(Constant.getThemes, new ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                listener.onGetThemesInfoFailed();
            }

            @Override
            public void onResponse(Response response) {
                try {
                    listener.onGetThemesInfoSuccess(gson.fromJson(response.body().string(), ThemesInfo.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void getThemeNewsContent(int id, final ThemeNewsContentListener listener) {
        OkHttpClientManager.getInstance().getEnqueue(Constant.getTheme + id, new ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                listener.onGetThemeNewsContenetFailed();
            }

            @Override
            public void onResponse(Response response) {
                try {
                    listener.onGetThemeNewsContentSuccess(gson.fromJson(response.body().string(), ThemeNewsContent.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
