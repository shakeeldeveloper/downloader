package com.example.downloader;

public class TikTokVideoInfo {
    private final String description;
    private final String thumbnailUrl;
    private final String videoUrl;

    public TikTokVideoInfo(String description, String thumbnailUrl, String videoUrl) {
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.videoUrl = videoUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

}
