package com.samli.samli.models;

public class Visual {
    int id;
    String title;
    String douban_id;
    String poster;

    public  Visual() {}

    public Visual(int id, String title, String douban_id, String poster) {
        this.id = id;
        this.title = title;
        this.douban_id = douban_id;
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
    public String getDouban_id() {
        return douban_id;
    }
    public void setDouban_id(String douban_id) {
        this.douban_id = douban_id;
    }
    public String getPoster() {
        return poster;
    }
    public void setPoster(String poster) {
        this.poster = poster;
    }
}
