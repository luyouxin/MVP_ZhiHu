package com.lzh.zhihu.model;

import java.util.List;

/**
 * Created by lzh on 2016/6/20.
 */

public class NewsInfo {
    private String title;
    private String ga_prefix;
    private int type;
    private int id;
    private List<String> images;

    public NewsInfo(String title, List<String> images, int id, int type, String ga_prefix) {
        this.title = title;
        this.images = images;
        this.id = id;
        this.type = type;
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
