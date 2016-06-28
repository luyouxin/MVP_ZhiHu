package com.lzh.zhihu.view;

import com.lzh.zhihu.model.ExtraInfo;

/**
 * Created by lzh on 2016/6/15.
 */
public interface NewsContentView {
    void webViewLoadUrl(String url);

    void initToolbar(ExtraInfo extraInfo);
}
