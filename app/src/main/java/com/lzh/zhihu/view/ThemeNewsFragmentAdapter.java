package com.lzh.zhihu.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzh.zhihu.R;
import com.lzh.zhihu.model.ThemeNewsContent;
import com.pkmmte.view.CircularImageView;

import java.util.List;

/**
 * Created by lzh on 2016/6/24.
 */
public class ThemeNewsFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private static final int TYPE_HEADITEM = 0x01;
    private static final int TYPE_ITEM = 0x02;
    private List<ThemeNewsContent.StoriesBean> storiesBeanList;
    private List<String> picUrl;
    private static final int ITEM_CLICK = 0x03;
    private static final int HEAD_CLICK = 0x04;
    private RecyclerViewItenListener recyclerViewItenListener;

    public ThemeNewsFragmentAdapter(List<ThemeNewsContent.StoriesBean> storiesBeanList, List<String> picUrl, Context mContext) {
        this.storiesBeanList = storiesBeanList;
        this.mContext = mContext;
        this.picUrl = picUrl;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADITEM;
        } else {
            return TYPE_ITEM;
        }
    }

    public void setOnItemClickListener(RecyclerViewItenListener recyclerViewItenListener) {
        this.recyclerViewItenListener = recyclerViewItenListener;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADITEM) {
            return new HeadViewHolder(LayoutInflater.from(mContext).inflate(R.layout.themenews_headview, parent, false));
        } else if (viewType == TYPE_ITEM) {
            return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item, parent, false));
        }
        return null;
    }

    private ThemeNewsContent.StoriesBean getItem(int position) {
        return storiesBeanList.get(position - 1);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof HeadViewHolder && picUrl.size() > 0) {
            for (int imagesize = 0; imagesize < picUrl.size() - 2; imagesize++) {
                LinearLayout imageLayout = (LinearLayout) ((HeadViewHolder) holder).imageLayout;
                CircularImageView imageView = new CircularImageView(mContext);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(90, 90);
                imageView.setLayoutParams(layoutParams);
                Glide.with(mContext).load(picUrl.get(imagesize + 2)).into(imageView);
                imageLayout.addView(imageView);
            }
            Glide.with(mContext).load(picUrl.get(0)).into(((HeadViewHolder) holder).descriptionImageView);
            ((HeadViewHolder) holder).titleTextView.setText(picUrl.get(1));
        } else if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).titleTextView.setText(storiesBeanList.get(position - 1).getTitle());
            if (storiesBeanList.get(position - 1).getImages() != null) {
                ((ItemViewHolder) holder).imageView.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(storiesBeanList.get(position - 1).getImages().get(0))
                        .into(((ItemViewHolder) holder).imageView);
            } else {
                ((ItemViewHolder) holder).imageView.setVisibility(View.GONE);
            }
            ((ItemViewHolder) holder).itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewItenListener.onItemClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return storiesBeanList.size() + 1;
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {
        ImageView descriptionImageView;
        TextView titleTextView;
        LinearLayout imageLayout;

        public HeadViewHolder(View headView) {
            super(headView);
            descriptionImageView = (ImageView) headView.findViewById(R.id.description_imageview);
            titleTextView = (TextView) headView.findViewById(R.id.title);
            imageLayout = (LinearLayout) headView.findViewById(R.id.image_layout);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView imageView;
        LinearLayout itemLayout;

        public ItemViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.title);
            imageView = (ImageView) itemView.findViewById(R.id.img);
            itemLayout = (LinearLayout) itemView.findViewById(R.id.item_view);
        }
    }
}

