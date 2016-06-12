package com.lzh.zhihu.model;

/**
 * Created by lzh on 2016/6/12.
 */
public interface GetHistoryNewsListener {
    void onGetHistoryNewsSuccess(HistoryNews historyNews);

    void onGetHistoryNewsFailed();
}
