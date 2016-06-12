package com.lzh.zhihu.model;

/**
 * Created by lzh on 2016/6/12.
 */
public interface GetNewsInteractor {
    void getCurrentDayNews(GetCurrentDayNewsListener listener);

    void getHistoryNews(GetHistoryNewsListener listener,String date);
}
