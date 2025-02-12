package com.example.downloader;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VideoDownloader extends AsyncTask<String, Void, String> {

    private static final String TAG = "VideoDownloader";
    private Context context;

    public VideoDownloader(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... urls) {
        String videoPageUrl = urls[0];
        try {
            // Fetch the TikTok page
            Document doc = Jsoup.connect(videoPageUrl).get();

            // Find the video URL
            Elements videoElements = doc.select("video[src]");
            if (videoElements.isEmpty()) {
                return "No video found";
            }

            String videoUrl = videoElements.attr("src");

            // Download the video
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(videoUrl).build();
            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                return "Failed to download video";
            }

            // Save the video to the device storage
            InputStream inputStream = response.body().byteStream();
            String videoFileName = "tiktok_video.mp4";
            File videoFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_MOVIES), videoFileName);
            FileOutputStream fileOutputStream = new FileOutputStream(videoFile);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            fileOutputStream.close();
            inputStream.close();

            return "Video downloaded: " + videoFile.getAbsolutePath();

        } catch (IOException e) {
            Log.e(TAG, "Error downloading video", e);
            return "Error downloading video";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.i(TAG, result);
        // Optionally, notify the user or update the UI
    }
}
