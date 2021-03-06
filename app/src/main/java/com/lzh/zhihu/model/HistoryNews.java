package com.lzh.zhihu.model;

import java.util.List;

/**
 * Created by lzh on 2016/6/12.
 */
public class HistoryNews {

    /**
     * date : 20131118
     * stories : [{"title":"深夜食堂 · 我的张曼妮","ga_prefix":"111822","images":["http://p4.zhimg.com/7b/c8/7bc8ef5947b069513c51e4b9521b5c82.jpg"],"type":0,"id":1747159}]
     */

    private String date;
    /**
     * title : 深夜食堂 · 我的张曼妮
     * ga_prefix : 111822
     * images : ["http://p4.zhimg.com/7b/c8/7bc8ef5947b069513c51e4b9521b5c82.jpg"]
     * type : 0
     * id : 1747159
     */

    private List<NewsInfo> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<NewsInfo> getStories() {
        return stories;
    }

    public void setStories(List<NewsInfo> stories) {
        this.stories = stories;
    }
}
