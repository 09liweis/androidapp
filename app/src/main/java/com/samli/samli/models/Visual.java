package com.samli.samli.models;

public class Visual {
    int id;
    String title;
    String doubanId;
    String poster;

    public  Visual() {}

    public Visual(int id, String title, String doubanId, String poster) {
        this.id = id;
        this.title = title;
        this.doubanId = doubanId;
        this.poster = poster;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDoubanId() {
        return doubanId;
    }
    public void setDoubanId(String doubanId) {
        this.doubanId = doubanId;
    }
    public String getPoster() {
        return poster;
    }
    public void setPoster(String poster) {
        this.poster = poster;
    }
}
