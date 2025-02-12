package com.example.downloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DownloadActivity extends AppCompatActivity {

    TextView home_text,files_text,setting_text;
    ConstraintLayout home,files,setting;
    ImageView home_icon,file_icon,setting_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame, new HomeFragment())
                    .commit();

        }
        home_icon=findViewById(R.id.VideoIcon);
        home=findViewById(R.id.video);
        home_text=findViewById(R.id.VideoText);
        files=findViewById(R.id.reels);
        files_text=findViewById(R.id.reelsText);
        file_icon=findViewById(R.id.reelsIcon);
        setting=findViewById(R.id.image);
        setting_icon=findViewById(R.id.ImageIcon);
        setting_text=findViewById(R.id.ImageText);

        home.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, new HomeFragment())
                        .commit();
                setting.setBackground(null);
                files.setBackground(null);
                home.setBackground(getDrawable(R.drawable.grediant));
                home_icon.setImageResource(R.drawable.clicked_hone);
                file_icon.setImageResource(R.drawable.folder);
                setting_icon.setImageResource(R.drawable.gear);
                home_text.setTextColor(getResources().getColor(R.color.white));
                files_text.setTextColor(R.color.sec);
                setting_text.setTextColor(R.color.sec);


            }
        });
        files.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables"})
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, new FileFragment())
                        .commit();
                home.setBackground(null);
                setting.setBackground(null);
                files.setBackground(getDrawable(R.drawable.grediant));
                home_icon.setImageResource(R.drawable.home);
                file_icon.setImageResource(R.drawable.clicked_folder);
                setting_icon.setImageResource(R.drawable.gear);
                home_text.setTextColor(R.color.sec);
                files_text.setTextColor(getResources().getColor(R.color.white));
                setting_text.setTextColor(R.color.sec);

            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables"})
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, new SettingFragment())
                        .commit();
                home.setBackground(null);
                files.setBackground(null);
                setting.setBackground(getDrawable(R.drawable.grediant));

                home_icon.setImageResource(R.drawable.home);
                file_icon.setImageResource(R.drawable.folder);
                setting_icon.setImageResource(R.drawable.clicked_gear);
                home_text.setTextColor(R.color.sec);
                files_text.setTextColor(R.color.sec);
                setting_text.setTextColor(getResources().getColor(R.color.white));

            }
        });
    }
}