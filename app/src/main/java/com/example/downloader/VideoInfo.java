package com.example.downloader;
class VideoInfo {
    private String title;
    private String thumbnail;
    private String link;
    private int duration;

    public VideoInfo(String title, String thumbnail, String link, int duration) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.link = link;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getLink() {
        return link;
    }

    public int getDuration() {
        return duration;
    }
}
