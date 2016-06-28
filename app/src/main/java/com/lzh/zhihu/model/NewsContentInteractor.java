package com.lzh.zhihu.model;

/**
 * Created by lzh on 2016/6/15.
 */
public interface NewsContentInteractor {
    void webViewLoadUrl(final NewsContentListener listener, String id);

    void getExtraData(final ExtraListener listener, String id);
}
