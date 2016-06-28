package com.lzh.zhihu.model;

/**
 * Created by lzh on 2016/6/24.
 */
public interface MainDrawerInteractor {
    void initDrawerData(final ThemesListener listener);

    void getThemeNewsContent(int id, final ThemeNewsContentListener listener);
}
