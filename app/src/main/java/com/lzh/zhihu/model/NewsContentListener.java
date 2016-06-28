package com.lzh.zhihu.model;

/**
 * Created by lzh on 2016/6/15.
 */
public interface NewsContentListener {
    void onLoadNewsContentSuccess(NewsContent newsContent);

    void onLoadNewsContentFailed();
}
