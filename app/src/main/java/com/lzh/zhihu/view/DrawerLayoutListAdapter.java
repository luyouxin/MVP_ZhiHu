package com.lzh.zhihu.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzh.zhihu.R;
import com.lzh.zhihu.model.ThemesInfo;

import java.util.List;

/**
 * Created by lzh on 2016/6/23.
 */
public class DrawerLayoutListAdapter extends BaseAdapter {
    private List<ThemesInfo.OthersBean> mList;
    private Handler mHandler;
    private Context mContext;
    private static final int ITEMCLICK = 0x02;

    public DrawerLayoutListAdapter(List<ThemesInfo.OthersBean> mList, Handler mHandler, Context context) {
        this.mList = mList;
        this.mHandler = mHandler;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.drawerlayout_list_item, null);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
            viewHolder.columnsImageView = (ImageView) convertView.findViewById(R.id.columns_image);
            viewHolder.columnsName = (TextView) convertView.findViewById(R.id.columns_name);
            viewHolder.itemLayout = (RelativeLayout) convertView.findViewById(R.id.item_layout);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {
            viewHolder.columnsImageView.setBackgroundResource(R.drawable.home_arrow);
        } else {
            viewHolder.columnsImageView.setBackgroundResource(R.drawable.menu_follow);
            viewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Message message = mHandler.obtainMessage();
                    message.what = ITEMCLICK;
                    message.arg1 = position;
                    mHandler.sendMessage(message);
                }
            });
        }
        viewHolder.columnsName.setText(mList.get(position).getName());
        return convertView;
    }

    class ViewHolder {
        TextView columnsName;
        ImageView columnsImageView;
        RelativeLayout itemLayout;
    }
}
