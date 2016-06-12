package com.lzh.zhihu.view;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzh.zhihu.R;
import com.lzh.zhihu.model.CurrentDayNews;

import java.util.List;

/**
 * Created by lzh on 2016/6/12.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<CurrentDayNews.StoriesBean> storiesBeanList;
    private Context mContext;
    private Handler mHandler;

    public RecyclerViewAdapter(List<CurrentDayNews.StoriesBean> storiesBeanList, Context mContext, Handler mHandler) {
        this.storiesBeanList = storiesBeanList;
        this.mContext = mContext;
        this.mHandler = mHandler;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.listview_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.titleTextView.setText(storiesBeanList.get(position).getTitle());
        Glide.with(mContext)
                .load(storiesBeanList.get(position).getImages().get(0))
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return storiesBeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            titleTextView = (TextView) view.findViewById(R.id.title);
            imageView = (ImageView) view.findViewById(R.id.img);
        }
    }
}
