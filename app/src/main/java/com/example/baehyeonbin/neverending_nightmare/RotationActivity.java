package com.example.baehyeonbin.neverending_nightmare;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class RotationActivity extends AppCompatActivity implements Animation.AnimationListener {

    private View rotationView;
    private int rotationCount = 3;
    private int curRotationCount = 0;
    private RotateAnimation rotate;
    private PieChart pieChart;
    private float degree = 0;
    private long duration = 700;
    private float deltaDegree = 129;
    private float deltaDeltaDegree = 3;
    private float targetDegree = 180;
    private long deltaTime = 1000 / 60;
    private int i = 0;
    private int j = 1;
    Handler handler;
    private Runnable runnable;
    private Runnable in;


    private RotateAnimation makeRotateAnimation(float fromDegrees, float toDegress) {
        RotateAnimation rotateAnimation =
                new RotateAnimation(fromDegrees, toDegress, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(duration);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setAnimationListener(RotationActivity.this);
        return rotateAnimation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            private Thread t;

            @Override
            public void onClick(final View view) {
                pieChart.setRotationAngle(0);
                degree = targetDegree - 346;
                duration = 700;
                deltaDegree = 129;
                deltaDeltaDegree = 3;
                handler.postAtFrontOfQueue(runnable);
//                t = new Thread(in);
//                t.start();
            }
        });

        handler = new Handler();

        rotationView = findViewById(R.id.rotation_view);
        pieChart = (PieChart) rotationView;
        String[] s = new String[]{"Green", "Yellow", "Red", "Blue", "HelloHelloHelloHelloHelloHello"};
        List<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < s.length; i++) {
            entries.add(new PieEntry((float) (100.0 / s.length), s[i]));
        }

        PieDataSet set = new PieDataSet(entries, "Election Results");
        set.setColors(ColorTemplate.VORDIPLOM_COLORS);
        PieData data = new PieData(set);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh
        pieChart.setCenterText("돌려돌려 돌림판~");
        pieChart.setHoleRadius(0);
        in = new Runnable() {
            @Override
            public void run() {
                while (deltaDegree > 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (deltaDegree > 5)
                        deltaDegree -= deltaDeltaDegree;
                    if (deltaDegree <= 5) {
                        deltaDeltaDegree = 0.5f;
                    }

                }
            }
        };
        runnable = new Runnable() {
            @Override
            public void run() {
                if (i == 6) {
                    i = 0;
                    deltaDegree -= deltaDeltaDegree;
                    if (deltaDegree < 0)
                        deltaDegree = 0;
                    if (deltaDegree <= 5) {
                        if (j == 1) {
                            j = 0;
                            deltaDegree = 5;
                        } else
                            deltaDeltaDegree = 0.5f;
                    }
                } else {
                    i++;
                }
                if (deltaDegree == 0) {
                    Log.d("tada", "run: " + degree);
                    return;
                }
                pieChart.setRotationAngle(degree);
                pieChart.invalidate();
                degree += deltaDegree;
                Log.d("ASD", "run: " + degree);
                handler.postDelayed(runnable, deltaTime);
            }
        };

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (curRotationCount < rotationCount) {
            curRotationCount++;
            duration += 250;
            rotate = makeRotateAnimation(rotationView.getRotation(), rotationView.getRotation() + 360);
            rotationView.startAnimation(rotate);
        } else if (curRotationCount == rotationCount) {
            curRotationCount++;
            RotateAnimation rotate2 =
                    new RotateAnimation(rotationView.getRotation(), (rotationView.getRotation() + 166), Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate2.setDuration(2000);
            rotate2.setInterpolator(new LinearInterpolator());
            rotate2.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    curRotationCount++;
                    rotationView.setRotation(166);
                    Toast.makeText(RotationActivity.this, "stop", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            rotationView.startAnimation(rotate2);

        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


}
