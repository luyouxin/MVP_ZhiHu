package com.lzh.zhihu.model;

/**
 * Created by lzh on 2016/6/24.
 */
public interface ThemeNewsContentListener {
    void onGetThemeNewsContentSuccess(ThemeNewsContent themeNewsContent);

    void onGetThemeNewsContenetFailed();
}
