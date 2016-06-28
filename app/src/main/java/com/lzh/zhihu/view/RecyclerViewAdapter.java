package com.lzh.zhihu.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bumptech.glide.Glide;
import com.lzh.zhihu.R;
import com.lzh.zhihu.model.CurrentDayNews;
import com.lzh.zhihu.model.NewsInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzh on 2016/6/12.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<NewsInfo> newsInfos;
    private Context mContext;
    private Handler mHandler;
    private RecyclerViewItenListener mainRecyclerViewItenListener;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOT = 2;
    private List<CurrentDayNews.TopStoriesBean> topStoriesBeanList;
    private ViewPager viewPager;
    private List<String> picUrl = new ArrayList<String>();

    public RecyclerViewAdapter(List<NewsInfo> storiesBeanList, List<CurrentDayNews.TopStoriesBean> topStoriesBeanList, Context mContext, Handler mHandler) {
        this.newsInfos = storiesBeanList;
        this.topStoriesBeanList = topStoriesBeanList;
        this.mContext = mContext;
        this.mHandler = mHandler;
    }

    public void setOnItemClickLitener(RecyclerViewItenListener mainRecyclerViewItenListener) {
        this.mainRecyclerViewItenListener = mainRecyclerViewItenListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        } else if (isPositionFooter(position)) {
            return TYPE_FOOT;
        }
        return TYPE_ITEM;
    }


    private NewsInfo getItem(int position) {
        return newsInfos.get(position - 1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item, parent, false));
        } else if (viewType == TYPE_HEADER) {
            //inflate your layout and pass it to view holder
            return new HeadViewHolder(LayoutInflater.from(mContext).inflate(R.layout.head_view, parent, false));
        } else if (viewType == TYPE_FOOT) {
            return new FootViewHolder(LayoutInflater.from(mContext).inflate(R.layout.foot_view, parent, false));
        }
        return null;
    }


    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position == newsInfos.size() + 1;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder && position > 0 && position < newsInfos.size() + 1) {
            ((ItemViewHolder) holder).imageView.setVisibility(View.VISIBLE);
            ((ItemViewHolder) holder).titleTextView.setText(newsInfos.get(position - 1).getTitle());
            Glide.with(mContext)
                    .load(newsInfos.get(position - 1).getImages().get(0))
                    .into(((ItemViewHolder) holder).imageView);
            ((ItemViewHolder) holder).itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainRecyclerViewItenListener.onItemClick(((ItemViewHolder) holder).itemLayout, position - 1);
                }
            });
        } else if (holder instanceof HeadViewHolder && position == 0) {
            for (CurrentDayNews.TopStoriesBean topStoriesBean : topStoriesBeanList) {
                picUrl.add(topStoriesBean.getImage());
            }
            ((HeadViewHolder) holder).banner.setVisibility(View.VISIBLE);
            ((HeadViewHolder) holder).banner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                @Override
                public NetworkImageHolderView createHolder() {
                    return new NetworkImageHolderView();
                }
            }, picUrl).setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                    //设置指示器的方向
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

            viewPager = ((HeadViewHolder) holder).banner.getViewPager();
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    ((HeadViewHolder) holder).titleTextView.setText(topStoriesBeanList.get(position).getTitle());
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } else if (holder instanceof FootViewHolder && position == newsInfos.size() + 2) {
            ((FootViewHolder) holder).textView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return newsInfos.size() + 1;
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

    class HeadViewHolder extends RecyclerView.ViewHolder {
        ConvenientBanner banner;
        TextView titleTextView;

        public HeadViewHolder(View headView) {
            super(headView);
            banner = (ConvenientBanner) headView.findViewById(R.id.convenientBanner);
            titleTextView = (TextView) headView.findViewById(R.id.title);
        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public FootViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.text_view);
        }
    }
}
