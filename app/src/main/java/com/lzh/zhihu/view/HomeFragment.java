package com.lzh.zhihu.view;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzh.zhihu.R;
import com.lzh.zhihu.model.CurrentDayNews;
import com.lzh.zhihu.model.HistoryNews;
import com.lzh.zhihu.model.NewsInfo;
import com.lzh.zhihu.presenter.HomeFragmentPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by lzh on 2016/6/22.
 */
public class HomeFragment extends Fragment implements HomeFragmentView {
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter adapter;
    private HomeFragmentPresenter homeFragmentPresenter;
    private List<NewsInfo> newsInfos;
    private CurrentDayNews mCurrentDayNews;
    private static final int INITRECYLERVIEW = 0x01;
    private static final int GETHISTORY = 0x02;
    private List<String> imgUrl;
    private LinearLayoutManager mLayoutManager;
    private SimpleDateFormat simpleDateFormat;
    private Date date;
    private int beforeDays = -1;
    private boolean isLoading = false;
    private List<CurrentDayNews.TopStoriesBean> topStoriesBeanList;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case INITRECYLERVIEW:
                    // newsInfos.addAll(((CurrentDayNews) msg.obj).getStories());
                    for (NewsInfo newsInfo : ((CurrentDayNews) msg.obj).getStories()) {
                        newsInfos.add(newsInfo);
                    }
                    for (CurrentDayNews.TopStoriesBean topStoriesBean : ((CurrentDayNews) msg.obj).getTop_stories()) {
                        topStoriesBeanList.add(topStoriesBean);
                    }
                    adapter.notifyDataSetChanged();
                    break;
                case GETHISTORY:
                    HistoryNews historyNews = (HistoryNews) msg.obj;
                    for (NewsInfo newsInfo : historyNews.getStories()) {
                        newsInfos.add(newsInfo);
                    }
                    //  newsInfos.addAll(historyNews.getStories());
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        date = new Date();
        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        newsInfos = new ArrayList<NewsInfo>();
        imgUrl = new ArrayList<String>();
        topStoriesBeanList = new ArrayList<CurrentDayNews.TopStoriesBean>();
        adapter = new RecyclerViewAdapter(newsInfos, topStoriesBeanList, getActivity(), mHandler);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(getActivity());
        //设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        homeFragmentPresenter = new HomeFragmentPresenter(this);
        homeFragmentPresenter.getCurrentDayNews();
        adapter.setOnItemClickLitener(new RecyclerViewItenListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                intent.putExtra("id", newsInfos.get(postion).getId());
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                int totalItemCount = mLayoutManager.getItemCount();
                if (lastVisibleItem + 1 == totalItemCount) {
                    if (!isLoading) {
                        isLoading = true;
                        homeFragmentPresenter.getHistoryNews(beforNumberDay(date, beforeDays));
                        Log.d("CESHI", "loadmore");
                    }
                }
            }
        });
    }

    @Override
    public void initRecyclerView(CurrentDayNews currentDayNews) {
        mCurrentDayNews = currentDayNews;
        Message msg = mHandler.obtainMessage();
        msg.what = INITRECYLERVIEW;
        msg.obj = mCurrentDayNews;
        mHandler.sendMessage(msg);

    }


    @Override
    public void updateRecyclerView(HistoryNews historyNews) {
        Message msg = mHandler.obtainMessage();
        msg.what = GETHISTORY;
        msg.obj = historyNews;
        mHandler.sendMessage(msg);
        beforeDays--;
        isLoading = false;
    }

    @Override
    public void toToast(String message) {

    }

    public String beforNumberDay(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_YEAR, day);
        return new SimpleDateFormat("yyyyMMdd").format(c.getTime());
    }

    @Override
    public void toActivity() {

    }
}
