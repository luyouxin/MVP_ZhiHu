package com.lzh.zhihu.presenter;

import com.lzh.zhihu.model.CurrentDayNews;
import com.lzh.zhihu.model.GetCurrentDayNewsListener;
import com.lzh.zhihu.model.GetHistoryNewsListener;
import com.lzh.zhihu.model.HistoryNews;
import com.lzh.zhihu.model.HomeFragmentInteractorImpl;
import com.lzh.zhihu.view.HomeFragmentView;

/**
 * Created by lzh on 2016/6/12.
 */
public class HomeFragmentPresenter {
    private HomeFragmentInteractorImpl mainInteractor;
    private HomeFragmentView homeFragmentView;
    private String message = "get message failed";

    public HomeFragmentPresenter(HomeFragmentView homeFragmentView) {
        this.homeFragmentView = homeFragmentView;
        this.mainInteractor = new HomeFragmentInteractorImpl();
    }

    public void getCurrentDayNews() {
        mainInteractor.getCurrentDayNews(new GetCurrentDayNewsListener() {
            @Override
            public void onGetCurrentDayNewsSuccess(CurrentDayNews currentDayNews) {
                homeFragmentView.initRecyclerView(currentDayNews);
            }

            @Override
            public void onGetCurrentDayNewsFailed() {
                homeFragmentView.toToast(message);
            }
        });
    }

    public void getHistoryNews(String date) {
        mainInteractor.getHistoryNews(new GetHistoryNewsListener() {
            @Override
            public void onGetHistoryNewsSuccess(HistoryNews historyNews) {
                homeFragmentView.updateRecyclerView(historyNews);
            }

            @Override
            public void onGetHistoryNewsFailed() {
                homeFragmentView.toToast(message);
            }
        }, date);
    }
}
