package com.lzh.zhihu.view;

import com.lzh.zhihu.model.ThemeNewsContent;

/**
 * Created by lzh on 2016/6/25.
 */
public interface ThemeNewsFragmentView {
    void toActivity();

    void updateRecyclerView(ThemeNewsContent themeNewsContent);

    void toToast(String message);
}
