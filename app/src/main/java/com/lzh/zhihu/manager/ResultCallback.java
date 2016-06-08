package com.lzh.zhihu.manager;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by lzh on 2016/4/27.
 */
public abstract class ResultCallback<T> {
    public abstract void onError(Call call, Exception e);

    public abstract void onResponse(Response response);
}
