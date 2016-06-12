package com.lzh.zhihu.view;

import com.lzh.zhihu.model.CurrentDayNews;
import com.lzh.zhihu.model.HistoryNews;

/**
 * Created by lzh on 2016/6/12.
 */
public interface MainView {
    void toActivity();

    void initRecyclerView(CurrentDayNews currentDayNews);

    void updateRecyclerView(HistoryNews historyNews);

    void toToast(String message);
}
