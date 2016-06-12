package com.lzh.zhihu.model;

/**
 * Created by lzh on 2016/6/12.
 */
public interface GetCurrentDayNewsListener {
    void onGetCurrentDayNewsSuccess(CurrentDayNews currentDayNews);

    void onGetCurrentDayNewsFailed();

}
