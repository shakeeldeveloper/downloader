package com.example.downloader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import yt_dlp;

public class YouTubeScraper {

    public static List<VideoInfo> scrapeYouTube(String videoUrl) {
        List<VideoInfo> videoInfoList = new ArrayList<>();
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "yt-dlp",
                    "--dump-json",
                    "--skip-download",
                    videoUrl
            );
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder jsonOutput = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonOutput.append(line);
            }

            String jsonString = jsonOutput.toString();

            // Parse JSON output
            if (jsonString.startsWith("[")) {
                // Multiple videos (e.g., playlist)
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    videoInfoList.add(parseVideoInfo(jsonObject));
                }
            } else {
                // Single video
                JSONObject jsonObject = new JSONObject(jsonString);
                videoInfoList.add(parseVideoInfo(jsonObject));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return videoInfoList;
    }

    private static VideoInfo parseVideoInfo(JSONObject jsonObject) {
        String title = jsonObject.optString("title", "N/A");
        String thumbnail = jsonObject.optString("thumbnail", "N/A");
        String link = jsonObject.optString("webpage_url", "N/A");
        int duration = jsonObject.optInt("duration", 0); // Duration in seconds

        return new VideoInfo(title, thumbnail, link, duration);
    }

    public static void main(String[] args) {
        String videoUrl = "https://www.youtube.com/watch?v=VIDEO_ID";
        List<VideoInfo> videos = scrapeYouTube(videoUrl);

        for (VideoInfo video : videos) {
            System.out.println("Title: " + video.getTitle());
            System.out.println("Thumbnail: " + video.getThumbnail());
            System.out.println("Link: " + video.getLink());
            System.out.println("Duration: " + video.getDuration() + " seconds");
            System.out.println("-------------------------");
        }
    }
}
