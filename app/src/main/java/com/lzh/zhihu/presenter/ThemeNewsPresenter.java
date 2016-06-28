package com.lzh.zhihu.presenter;

import com.lzh.zhihu.model.MainDrawerInteractorImpl;
import com.lzh.zhihu.model.ThemeNewsContent;
import com.lzh.zhihu.model.ThemeNewsContentListener;
import com.lzh.zhihu.view.ThemeNewsFragmentView;

/**
 * Created by lzh on 2016/6/25.
 */
public class ThemeNewsPresenter {
    private MainDrawerInteractorImpl mainDrawerInteractor;
    private ThemeNewsFragmentView themeNewsFragmentView;
    private String message = "get message failed";

    public ThemeNewsPresenter(ThemeNewsFragmentView themeNewsFragmentView) {
        this.themeNewsFragmentView = themeNewsFragmentView;
        mainDrawerInteractor = new MainDrawerInteractorImpl();
    }

    public void getThemeNewsContent(int id) {
        mainDrawerInteractor.getThemeNewsContent(id, new ThemeNewsContentListener() {
            @Override
            public void onGetThemeNewsContentSuccess(ThemeNewsContent themeNewsContent) {
                themeNewsFragmentView.updateRecyclerView(themeNewsContent);
            }

            @Override
            public void onGetThemeNewsContenetFailed() {
                themeNewsFragmentView.toToast(message);
            }
        });
    }
}
