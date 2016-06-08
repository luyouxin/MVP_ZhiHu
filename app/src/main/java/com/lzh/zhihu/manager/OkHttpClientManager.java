package com.lzh.zhihu.manager;


import com.lzh.zhihu.model.ParamInfo;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lzh on 2016/4/27.
 */
public class OkHttpClientManager {
    private static OkHttpClientManager instance;
    private OkHttpClient okHttpClient;
    private ResultCallback resultCallback;

    public OkHttpClientManager() {
        okHttpClient = new OkHttpClient();
    }

    public static OkHttpClientManager getInstance() {
        if (instance == null) {
            synchronized (OkHttpClientManager.class) {
                if (instance == null) {
                    instance = new OkHttpClientManager();
                }
            }
        }
        return instance;
    }

    public Response getResponse(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        Response execute = call.execute();
        return execute;
    }

    public String getString(String url) throws IOException {
        Response execute = getResponse(url);
        return execute.body().string();
    }


    public void getEnqueue(String url, final ResultCallback callback) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
                                                  @Override
                                                  public void onFailure(Call call, IOException e) {
                                                      callback.onError(call, e);
                                                  }

                                                  @Override
                                                  public void onResponse(Call call, Response response) throws IOException {
                                                      callback.onResponse(response);
                                                  }
                                              }
        );
    }

    public Response postResponse(String url, ParamInfo[] params) throws IOException {
        Request request = buildPostRequest(url, params);
        Response response = okHttpClient.newCall(request).execute();
        return response;
    }

    private String postString(String url, ParamInfo[] params) throws IOException {
        Response response = postResponse(url, params);
        return response.body().string();
    }

    public void postEnqueue(String url, final ResultCallback callback, ParamInfo[] params) {
        Request request = buildPostRequest(url, params);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callback.onResponse(response);
            }
        });
    }

    private Request buildPostRequest(String url, ParamInfo[] params) {
        FormBody.Builder builder = new FormBody.Builder();
        for (ParamInfo param : params) {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }
}
