package com.study.riseof.news.model;

public class MeduzaCutNews {

    String title;
    String link;
    String guid;
    String enclosure;
    String description;
    long length;
    String type;
    String url;
    String pubDate;

    public MeduzaCutNews() {
    }

    public MeduzaCutNews(String title, String link, String guid, String enclosure, String description, long length, String type, String url, String pubDate) {
        this.title = title;
        this.link = link;
        this.guid = guid;
        this.enclosure = enclosure;
        this.description = description;
        this.length = length;
        this.type = type;
        this.url = url;
        this.pubDate = pubDate;
    }
}
