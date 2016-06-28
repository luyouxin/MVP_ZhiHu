package com.lzh.zhihu.view;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.lzh.zhihu.R;
import com.lzh.zhihu.model.ThemesInfo;
import com.lzh.zhihu.presenter.MainPresenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {
    private HomeFragment homeFragment;
    private Toolbar mToolBar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mListView;
    private View headView;
    private List<String> mList;
    private List<ThemesInfo.OthersBean> othersBeanList;
    private DrawerLayoutListAdapter drawerLayoutListAdapter;
    private MainPresenter mainPresenter;
    private static final int INITDRAWERLAYOUT = 0x01;
    private static final int ITEMCLICK = 0x02;
    private int selectThemeID;
    private ThemesInfo themesInfo;
    private ThemeNewsFragment themeNewsFragment;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case INITDRAWERLAYOUT:
                    themesInfo = (ThemesInfo) msg.obj;
                    if (themesInfo.getOthers().size() != 0) {
                        othersBeanList.addAll(themesInfo.getOthers());
                        drawerLayoutListAdapter.notifyDataSetChanged();
                    }
                    break;
                case ITEMCLICK:
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    selectThemeID = othersBeanList.get(msg.arg1).getId();
                  //  homeFragment = new HomeFragment();
                       themeNewsFragment = new ThemeNewsFragment();
                    transaction.replace(R.id.framelayout, themeNewsFragment);
                    transaction.commit();
                    mDrawerLayout.closeDrawers();
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
        homeFragment = new HomeFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.framelayout, homeFragment);
        transaction.commit();
        mainPresenter = new MainPresenter(this);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mListView = (ListView) findViewById(R.id.listview);
        mToolBar.setOnMenuItemClickListener(onMenuItemClick);
        mToolBar.setTitle("");
        setSupportActionBar(mToolBar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerlayout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar, R.string.open, R.string.close) {
            @Override
            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
            }
        };
        ActionBar actionBar = getSupportActionBar();
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        headView = LayoutInflater.from(this).inflate(R.layout.drawerlayout_headview, null);
        mListView.addHeaderView(headView);
        othersBeanList = new ArrayList<ThemesInfo.OthersBean>();
        drawerLayoutListAdapter = new DrawerLayoutListAdapter(othersBeanList, mHandler, this);
        mListView.setAdapter(drawerLayoutListAdapter);
        mainPresenter.initDrawerData();
    }

    @Override
    public void initDrawerLayou(ThemesInfo themesInfo) {
        Message message = mHandler.obtainMessage();
        message.what = INITDRAWERLAYOUT;
        message.obj = themesInfo;
        mHandler.sendMessage(message);
    }


    @Override
    public void toToast(String message) {

    }

    public int getThemeID() {
        return selectThemeID;
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
            }

            if (!msg.equals("")) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

}
