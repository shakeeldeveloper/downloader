package com.example.downloader;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Integer, String> {
    @Override
    protected String doInBackground(String... urls) {
        String videoUrl = urls[0];
        String fileName = "tiktok_video.mp4";
        try {
            URL url = new URL(videoUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            int fileLength = connection.getContentLength();
            File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
            FileOutputStream fos = new FileOutputStream(outputFile);
            InputStream is = connection.getInputStream();

            byte[] buffer = new byte[1024];
            int len;
            long total = 0;
            while ((len = is.read(buffer)) != -1) {
                total += len;
                publishProgress((int) (total * 100 / fileLength));
                fos.write(buffer, 0, len);
            }

            fos.close();
            is.close();
            return outputFile.getAbsolutePath();
        } catch (Exception e) {
            // Handle exceptions (e.g., invalid URL or no internet connection)
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int progress = values[0]; // Get the progress value (percentage)
        Log.e("progress",String.valueOf(progress));
        // Update UI with download progress (if needed)
    }

    @Override
    protected void onPostExecute(String filePath) {
        super.onPostExecute(filePath);
        if (filePath != null) {
            Log.e("progress","downloaded");
            // File downloaded successfully, use the filePath as needed
        } else {
            // Handle download failure
        }
    }
}
