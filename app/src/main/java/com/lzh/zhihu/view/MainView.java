package com.lzh.zhihu.view;

import com.lzh.zhihu.model.ThemeNewsContent;
import com.lzh.zhihu.model.ThemesInfo;

/**
 * Created by lzh on 2016/6/12.
 */
public interface MainView {
    void initDrawerLayou(ThemesInfo themesInfo);


    void toToast(String message);

}
