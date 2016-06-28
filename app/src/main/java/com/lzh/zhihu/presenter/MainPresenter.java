package com.lzh.zhihu.presenter;

import com.lzh.zhihu.model.MainDrawerInteractorImpl;
import com.lzh.zhihu.model.ThemeNewsContent;
import com.lzh.zhihu.model.ThemeNewsContentListener;
import com.lzh.zhihu.model.ThemesInfo;
import com.lzh.zhihu.model.ThemesListener;
import com.lzh.zhihu.view.MainView;

/**
 * Created by lzh on 2016/6/24.
 */
public class MainPresenter {
    private MainDrawerInteractorImpl mainDrawerInteractor;
    private MainView mainView;
    private String message = "get message failed";

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
        mainDrawerInteractor = new MainDrawerInteractorImpl();
    }

    public void initDrawerData() {
        mainDrawerInteractor.initDrawerData(new ThemesListener() {
            @Override
            public void onGetThemesInfoSuccess(ThemesInfo themesInfo) {
                mainView.initDrawerLayou(themesInfo);
            }

            @Override
            public void onGetThemesInfoFailed() {
                mainView.toToast(message);
            }
        });
    }


}
