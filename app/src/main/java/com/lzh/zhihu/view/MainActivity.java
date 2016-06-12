package com.lzh.zhihu.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.lzh.zhihu.R;
import com.lzh.zhihu.model.CurrentDayNews;
import com.lzh.zhihu.model.HistoryNews;
import com.lzh.zhihu.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainView {
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter adapter;
    private MainPresenter mainPresenter;
    private static final int INITRECYLERVIEW = 0x01;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case INITRECYLERVIEW:
                    adapter = new RecyclerViewAdapter(((CurrentDayNews) msg.obj).getStories(), MainActivity.this, mHandler);
                    mRecyclerView.setAdapter(adapter);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        //设置布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mainPresenter = new MainPresenter(this);
        mainPresenter.getCurrentDayNews();
    }

    @Override
    public void toActivity() {

    }

    @Override
    public void initRecyclerView(CurrentDayNews currentDayNews) {
        Message msg = mHandler.obtainMessage();
        msg.what = INITRECYLERVIEW;
        msg.obj = currentDayNews;
        mHandler.sendMessage(msg);

    }

    @Override
    public void updateRecyclerView(HistoryNews historyNews) {

    }

    @Override
    public void toToast(String message) {

    }
}
