package com.lzh.zhihu.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzh.zhihu.R;
import com.lzh.zhihu.model.ExtraInfo;
import com.lzh.zhihu.presenter.NewsContentPresenter;

/**
 * Created by lzh on 2016/6/15.
 */
public class NewsActivity extends Activity implements NewsContentView, View.OnClickListener {
    private WebView webView;
    private int id;
    private NewsContentPresenter newsContentPresenter = new NewsContentPresenter(this);
    private static final int LOADWEBVIEW = 0x01;
    private static final int INITTOOLBAR = 0x02;
    private ImageView backImageView, shareImageView, collectImageView, commentImageView, praiseImageView;
    private TextView commnetNumTextView, praiseNumTextView;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOADWEBVIEW:
                    String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
                    String html = "<html><head>" + css + "</head><body>" + (String) msg.obj + "</body></html>";
                    html = html.replace("<div class=\"img-place-holder\">", "");
                    webView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
                    break;
                case INITTOOLBAR:
                    ExtraInfo extraInfo = (ExtraInfo) msg.obj;
                    commnetNumTextView.setText(extraInfo.getComments() + "");
                    praiseNumTextView.setText(extraInfo.getPopularity() + "");
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initViews();
        setListener();
    }

    private void initViews() {
        id = getIntent().getIntExtra("id", -1);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbarlayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        webView = (WebView) findViewById(R.id.webview);
        backImageView = (ImageView) findViewById(R.id.back);
        shareImageView = (ImageView) findViewById(R.id.share);
        collectImageView = (ImageView) findViewById(R.id.collect);
        praiseImageView = (ImageView) findViewById(R.id.praise);
        commnetNumTextView = (TextView) findViewById(R.id.comment_num);
        praiseNumTextView = (TextView) findViewById(R.id.praise_num);
        newsContentPresenter.loadWebViewUrl(String.valueOf(id));
        newsContentPresenter.initToolbar(String.valueOf(id));
        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 开启DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
        // 开启database storage API功能
        webView.getSettings().setDatabaseEnabled(true);
        // 开启Application Cache功能
        webView.getSettings().setAppCacheEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

//        // AppBar的监听
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                int maxScroll = appBarLayout.getTotalScrollRange();
//                float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
//                Log.d("CESHI", percentage + "");
//                if (percentage == 0) {
//                    toolbar.setBackgroundResource(R.color.blue);
//                    Log.d("CESHI", 0 + "aa");
//                } else {
//                    toolbar.setBackgroundColor(Color.argb((int) (percentage * 255), 0, 162, 234));
//                }
//            }
//        });

    }

    private void setListener() {
        backImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void webViewLoadUrl(String url) {
        Message msg = mHandler.obtainMessage();
        msg.what = LOADWEBVIEW;
        msg.obj = url;
        mHandler.sendMessage(msg);
    }

    @Override
    public void initToolbar(ExtraInfo extraInfo) {
        Message msg = mHandler.obtainMessage();
        msg.what = INITTOOLBAR;
        msg.obj = extraInfo;
        mHandler.sendMessage(msg);
    }
}
