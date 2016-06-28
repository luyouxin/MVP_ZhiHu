package com.lzh.zhihu.view;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzh.zhihu.R;
import com.lzh.zhihu.model.ThemeNewsContent;
import com.lzh.zhihu.presenter.ThemeNewsPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzh on 2016/6/24.
 */
public class ThemeNewsFragment extends Fragment implements ThemeNewsFragmentView {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private ThemeNewsFragmentAdapter mThemeNewsFragmentAdapter;
    private ThemeNewsContent mThemeNewsContent;
    private ThemeNewsPresenter themeNewsPresenter;
    private int themeID;
    private List<ThemeNewsContent.StoriesBean> storiesBeanList;
    private List<String> picUrl;
    private static final int UPDATARECYCLERVIEW = 0x01;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATARECYCLERVIEW:
                    mThemeNewsContent = (ThemeNewsContent) msg.obj;
                    for (ThemeNewsContent.StoriesBean storiesBean : mThemeNewsContent.getStories()) {
                        storiesBeanList.add(storiesBean);
                    }
                    picUrl.add(mThemeNewsContent.getBackground());
                    picUrl.add(mThemeNewsContent.getDescription());
                    for (ThemeNewsContent.EditorsBean editorsBean : mThemeNewsContent.getEditors()) {
                        picUrl.add(editorsBean.getAvatar());
                    }
                    mThemeNewsFragmentAdapter.notifyDataSetChanged();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        themeID = ((MainActivity) getActivity()).getThemeID();
        storiesBeanList = new ArrayList<ThemeNewsContent.StoriesBean>();
        picUrl = new ArrayList<String>();
        mThemeNewsFragmentAdapter = new ThemeNewsFragmentAdapter(storiesBeanList, picUrl, getActivity());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        //设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(mThemeNewsFragmentAdapter);
        themeNewsPresenter = new ThemeNewsPresenter(this);
        themeNewsPresenter.getThemeNewsContent(themeID);
        mThemeNewsFragmentAdapter.setOnItemClickListener(new RecyclerViewItenListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                intent.putExtra("id", storiesBeanList.get(postion).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void toActivity() {

    }

    @Override
    public void updateRecyclerView(ThemeNewsContent themeNewsContent) {
        Message message = mHandler.obtainMessage();
        message.what = UPDATARECYCLERVIEW;
        message.obj = themeNewsContent;
        mHandler.sendMessage(message);
    }

    @Override
    public void toToast(String message) {

    }
}
