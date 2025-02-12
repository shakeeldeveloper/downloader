package com.example.downloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout start;
    ImageView circle1,circle2,circle3;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circle1=findViewById(R.id.imageView);
        circle2=findViewById(R.id.imageView1);
        circle3=findViewById(R.id.imageView2);
        start=findViewById(R.id.getstart);
        RotateAnimation rotateAnimation = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(10000); // duration in milliseconds
        rotateAnimation.setRepeatCount(Animation.INFINITE); // repeat forever
        circle1.startAnimation(rotateAnimation);
        RotateAnimation rotatAnimation = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotatAnimation.setDuration(10000); // duration in milliseconds
        rotatAnimation.setRepeatCount(Animation.INFINITE); // repeat forever
        circle3.startAnimation(rotatAnimation);
        RotateAnimation rotate = new RotateAnimation(
                360, 0,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(10000); // duration in milliseconds
        rotate.setRepeatCount(Animation.INFINITE); // repeat forever

        // Start the animation
        circle2.startAnimation(rotate);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
     //           rotate.cancel();
       //         rotateAnimation.cancel();

            }
        }, 1000);

  /*      if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

// Call the Python function
        Python python = Python.getInstance();
        int num1 = 10;
        int num2 = 20;
        int sum = python.getModule("youtube").callAttr("add_numbers", num1, num2).toInt();
*/
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DownloadActivity.class));
            }
        });
    }
}