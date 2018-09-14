package com.example.baehyeonbin.neverending_nightmare;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

public class RotationActivity extends AppCompatActivity {

    private ImageView imageView;
    private int rotationCount = 3;
    private long duration = 700;
    private int curRotationCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curRotationCount = 0;
                final RotateAnimation rotate =
                        new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(duration);
                rotate.setInterpolator(new LinearInterpolator());
                rotate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (curRotationCount < rotationCount) {
                            curRotationCount++;
                            duration += 250;
                            rotate.setDuration(duration);
                            imageView.startAnimation(rotate);
                        } else if (curRotationCount == rotationCount) {
                            curRotationCount++;
                            RotateAnimation rotate2 =
                                    new RotateAnimation(0, 166, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            rotate2.setDuration(2000);
                            rotate2.setInterpolator(new LinearInterpolator());
                            rotate2.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    curRotationCount++;
                                    imageView.setRotation(166);
                                    Toast.makeText(RotationActivity.this, "stop", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            imageView.startAnimation(rotate2);

                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                imageView.startAnimation(rotate);

            }
        });

        imageView = findViewById(R.id.imgview);


    }

}
