package com.lzh.zhihu.model;

/**
 * Created by lzh on 2016/6/27.
 */
public interface ExtraListener {
    void onGetExtraSuccess(ExtraInfo extraInfo);

    void onGerExtraFailed();
}
