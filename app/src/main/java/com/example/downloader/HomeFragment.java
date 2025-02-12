package com.example.downloader;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static androidx.core.content.ContextCompat.getSystemService;

import static com.example.downloader.YouTubeScraper.scrapeYouTube;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    private static final String API_KEY = "AIzaSyBDPF0YIAEwslqnWuOJDoMptH9GunVhex0";
    private static String VIDEO_ID = "YOUR_VIDEO_ID"; // Extract this from the YouTube link

    ConstraintLayout paste, start, cancel, download, cross, video, audio, detail, trans;
    EditText search;
    List<VideoInfo> videos;
    TextView video_title,audio_Size,video_Size,dur;
    private ClipboardManager clipboardManager;
    AlertDialog dialog;
    View dialogView;
    private Handler handler = new Handler();
    ImageView video_radio, audio_radio, video_img;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        clipboardManager = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
        paste = view.findViewById(R.id.paste);
        start = view.findViewById(R.id.start);
        search = view.findViewById(R.id.search);
        paste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clipboardManager.hasPrimaryClip() && clipboardManager.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    ClipData.Item item = clipboardManager.getPrimaryClip().getItemAt(0);
                    String copiedText = item.getText().toString();
                    search.setText(copiedText);
                } else {
                    Toast.makeText(getContext(), "No link copied!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(search.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Paste link!", Toast.LENGTH_SHORT).show();

                }


            //    new FetchVideoDetailsTask().execute(search.getText().toString());
   /*             String videoUrl = search.getText().toString();
                new VideoDownloader(getContext()).execute(videoUrl);
*/
            /*    new DownloadTask().execute(search.getText().toString()); // Start download in background
            */
                String videoUrl = search.getText().toString();
                videos = scrapeYouTube(videoUrl);
                showCustomDialog();
             /*   Animation slideUp = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
                detail.startAnimation(slideUp);
                detail.setVisibility(View.VISIBLE);
                cross.startAnimation(slideUp);
                cross.setVisibility(View.VISIBLE);
                trans.setVisibility(View.VISIBLE);
            */}
        });
        return view;
    }

    private Activity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }
        private void showCustomDialog() {
        // Inflate the dialog layout
        dialogView = LayoutInflater.from(getContext()).inflate(R.layout.detail, null);

        // Create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), R.style.CustomTransparentDialog);
        builder.setView(dialogView);
        dialog = builder.create();
        cross=dialogView.findViewById(R.id.constraintLayout);
        video_radio = dialogView.findViewById(R.id.video_radio_btn);
        audio_radio = dialogView.findViewById(R.id.audio_radio_btn);
        video_img = dialogView.findViewById(R.id.video_image);
        video_title = dialogView.findViewById(R.id.video_title);
        cancel = dialogView.findViewById(R.id.cancel);
        download = dialogView.findViewById(R.id.download);
        video = dialogView.findViewById(R.id.video_btn);
        audio = dialogView.findViewById(R.id.audio_btn);
        audio_Size = dialogView.findViewById(R.id.audio_size);
        video_Size = dialogView.findViewById(R.id.video_size);
        dur = dialogView.findViewById(R.id.duration);

                for (VideoInfo video : videos) {
                    Glide.with(this)
                            .load(video.getLink())
                            .placeholder(R.drawable.video_photo) // optional
                            .error(R.drawable.video_photo)           // optional
                            .into(video_img);
                    video_title.setText(video.getTitle());
                    dur.setText(video.getDuration());
                    System.out.println("Title: " + video.getTitle());
                    System.out.println("Thumbnail: " + video.getThumbnail());
                    System.out.println("Link: " + video.getLink());
                    System.out.println("Duration: " + video.getDuration() + " seconds");
                    System.out.println("-------------------------");
                }

     /*   DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(search.getText().toString());
     */   //String url = search.getText().toString(); // Replace with the actual video URL
       // new VideoScraperTask(video_title, dur, video_Size, audio_Size, video_img).execute(url);
        audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audio_radio.setVisibility(View.VISIBLE);
                video_radio.setVisibility(View.GONE);
            }
        });
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audio_radio.setVisibility(View.GONE);
                video_radio.setVisibility(View.VISIBLE);
            }
        });
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation slideDown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);
                dialogView.startAnimation(slideDown);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, 500);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation slideDown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);
                dialogView.startAnimation(slideDown);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, 500);
            }
        });
        dialog.show();
        // Set the dialog position and size to fully occupy the top right corner
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT; // Set the width to your desired size, e.g., 400 pixels
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT; // Adjust height as needed
        layoutParams.x = 10; // Adjust horizontal offset if necessary
        layoutParams.y = 10; // Adjust vertical offset if necessary
        dialog.getWindow().setAttributes(layoutParams);

        // Apply the animation
        Animation slideDown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
        dialogView.startAnimation(slideDown);
    }


   /* private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String videorl = urls[0];
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://www.facebook.com/video.php?v=VIDEO_ID")
                        .build();
                Response response = client.newCall(request).execute();

                // Parse HTML content using Jsoup
                Document doc = Jsoup.parse(response.body().string());
                String videoSourceLink = doc.select("source[type=\"video/mp4\"]").attr("src");

                // Download video using OkHttp
                Request videoRequest = new Request.Builder()
                        .url(videoSourceLink)
                        .build();
                Response videoResponse = client.newCall(videoRequest).execute();
                // Save the video to a file
                FileOutputStream fos = new FileOutputStream("video.mp4");
                fos.write(videoResponse.body().bytes());
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(String downloadedFilePath) {
            if (downloadedFilePath != null) {
                // Download successful
                Log.d(TAG, "Video downloaded to: " + downloadedFilePath);
                // Update UI with downloaded file path (optional)
                // You can show a success message or perform actions with the downloaded file here
            } else {
                // Download failed
                Log.w(TAG, "Download failed!");
                // Update UI with error message (optional)
            }
        }
    }
*/
    // Download logic (replace with your actual implementation)
    private String downloadVideo(String videoUrl) throws IOException {
        URL url = new URL(videoUrl);
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();

        int contentLength = urlConnection.getContentLength();

        if (contentLength <= 0) {
            throw new IOException("Invalid content length");
        }

        // Check for storage permission (you'll need to implement this)
        if (!checkStoragePermission(getContext())) {
            // Request permission here
            return null;
        }

        String fileName = "downloaded_video.mp4";
        FileOutputStream outputStream = activity.openFileOutput(fileName, Context.MODE_PRIVATE);

        byte[] buffer = new byte[1024];
        int bytesRead;
        long downloaded = 0;

        while ((bytesRead = urlConnection.getInputStream().read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
            downloaded += bytesRead;
            Log.d(TAG, "Downloaded: " + downloaded + " / " + contentLength);
        }

        outputStream.flush();
        outputStream.close();

        return fileName;
    }

    // Implement storage permission check (optional)
    private boolean checkStoragePermission(Context context) {
        // ... (permission check logic)
        return true; // Replace with your actual permission check
    }


}