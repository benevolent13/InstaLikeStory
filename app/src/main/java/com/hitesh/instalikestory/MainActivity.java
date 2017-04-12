package com.hitesh.instalikestory;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textViewCount;
    ImageView ivMainImage;
    View viewLeft,viewRight;
    List<String> storyModels = new ArrayList<>();
    int size,count = 0;
    private CountDownTimer timer;
    private ProgressBar progressImageBar;
    private static int TIMER = 8000; //wait for 8 seconds

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_layout);

        progressImageBar = (ProgressBar)findViewById(R.id.progressBarStory);
        viewLeft = (View)findViewById(R.id.viewLeft);
        viewRight = (View)findViewById(R.id.viewRight);
        ivMainImage = (ImageView)findViewById(R.id.ivStory);
        textViewCount = (TextView)findViewById(R.id.tvCount);

        //view touch for previous and next images
        viewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count==size-1){
                    Toast.makeText(getApplicationContext(),"Last Image",Toast.LENGTH_SHORT).show();
                }else {
                    timer.cancel();
                    count = count +1;
                    startStory(count);
                }
            }
        });

        viewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count==0){
                    Toast.makeText(getApplicationContext(),"First Image",Toast.LENGTH_SHORT).show();
                }else {
                    timer.cancel();
                    count = count -1;
                    startStory(count);
                }

            }
        });

        getImages();


    }

    private void getImages() {
        //set your urls in this
        storyModels.add("http://keenthemes.com/preview/metronic/theme/assets/global/plugins/jcrop/demos/demo_files/image2.jpg");
        storyModels.add("http://7606-presscdn-0-74.pagely.netdna-cdn.com/wp-content/uploads/2016/03/Dubai-Photos-Images-Dubai-City-HD-Wallpaper-800x600.jpg");
        storyModels.add("http://economictimes.indiatimes.com/thumb/msid-49212668,width-640,resizemode-4/spectacular-images-albuquerque-international-balloon-fiesta.jpg");
        storyModels.add("https://cdn.pixabay.com/photo/2016/06/29/17/14/water-1487304_960_720.jpg");
        size = storyModels.size();
        startStory(count);
    }

    private void startStory(int count) {
        progressImageBar.setVisibility(View.VISIBLE);
        Log.i("url image",storyModels.get(count));
        Glide.with(getApplicationContext()).load(storyModels.get(count))
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressImageBar.setVisibility(View.INVISIBLE);
                        startTimer();
                        return false;
                    }
                })
                .crossFade()
                .into(ivMainImage);
    }

    private void startTimer() {
        timer = new CountDownTimer(TIMER,1000){
            @Override
            public void onFinish() {
                textViewCount.setText("Loading..");
                if(count==size-1){
                    finish();
                    Toast.makeText(getApplicationContext(),"Story Finished",Toast.LENGTH_SHORT).show();
                }else{
                    count = count + 1;
                    startStory(count);
                }

            }

            @Override
            public void onTick(long millisUntilFinished) {
                textViewCount.setText("" + millisUntilFinished / 1000);
            }
        }.start();
    }

}

