package com.lzh.zhihu.presenter;

import android.util.Log;

import com.lzh.zhihu.model.ExtraInfo;
import com.lzh.zhihu.model.ExtraListener;
import com.lzh.zhihu.model.NewsContent;
import com.lzh.zhihu.model.NewsContentInteractorImpl;
import com.lzh.zhihu.model.NewsContentListener;
import com.lzh.zhihu.view.NewsContentView;

/**
 * Created by lzh on 2016/6/15.
 */
public class NewsContentPresenter {
    private NewsContentInteractorImpl newsContentInteractorImpl;
    private NewsContentView newsContentView;

    public NewsContentPresenter(NewsContentView newsContentView) {
        this.newsContentView = newsContentView;
        newsContentInteractorImpl = new NewsContentInteractorImpl();
    }

    public void loadWebViewUrl(String id) {
        Log.d("CESHI", id + "NewsContentPresenter");
        newsContentInteractorImpl.webViewLoadUrl(new NewsContentListener() {
            @Override
            public void onLoadNewsContentSuccess(NewsContent newsContent) {
                newsContentView.webViewLoadUrl(newsContent.getBody());
            }

            @Override
            public void onLoadNewsContentFailed() {

            }
        }, id);
    }

    public void initToolbar(String id) {
        newsContentInteractorImpl.getExtraData(new ExtraListener() {
            @Override
            public void onGetExtraSuccess(ExtraInfo extraInfo) {
                newsContentView.initToolbar(extraInfo);
            }

            @Override
            public void onGerExtraFailed() {

            }
        }, id);
    }
}
