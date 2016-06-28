package com.lzh.zhihu.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by lzh on 2016/6/17.
 */
public class NScrollRecyclerView extends RecyclerView {
    public NScrollRecyclerView(Context context) {
        super(context);
    }

    public NScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NScrollRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return false;
    }
}
