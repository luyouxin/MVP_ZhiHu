package com.lzh.zhihu.presenter;

import com.lzh.zhihu.model.CurrentDayNews;
import com.lzh.zhihu.model.GetCurrentDayNewsListener;
import com.lzh.zhihu.model.GetHistoryNewsListener;
import com.lzh.zhihu.model.HistoryNews;
import com.lzh.zhihu.model.MainInteractorImpl;
import com.lzh.zhihu.view.MainView;

/**
 * Created by lzh on 2016/6/12.
 */
public class MainPresenter {
    private MainInteractorImpl mainInteractor;
    private MainView mainView;
    private String message = "get message failed";

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
        this.mainInteractor = new MainInteractorImpl();
    }

    public void getCurrentDayNews() {
        mainInteractor.getCurrentDayNews(new GetCurrentDayNewsListener() {
            @Override
            public void onGetCurrentDayNewsSuccess(CurrentDayNews currentDayNews) {
                mainView.initRecyclerView(currentDayNews);
            }

            @Override
            public void onGetCurrentDayNewsFailed() {
                mainView.toToast(message);
            }
        });
    }

    public void getHistoryNews(String date) {
        mainInteractor.getHistoryNews(new GetHistoryNewsListener() {
            @Override
            public void onGetHistoryNewsSuccess(HistoryNews historyNews) {
                mainView.updateRecyclerView(historyNews);
            }

            @Override
            public void onGetHistoryNewsFailed() {
                mainView.toToast(message);
            }
        }, date);
    }
}
