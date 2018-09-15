package com.example.baehyeonbin.neverending_nightmare;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
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

import com.example.baehyeonbin.neverending_nightmare.beans.RoomWallet;
import com.example.baehyeonbin.neverending_nightmare.beans.User;
import com.example.baehyeonbin.neverending_nightmare.beans.WalletRoomResponse;
import com.example.baehyeonbin.neverending_nightmare.services.RoomService;
import com.example.baehyeonbin.neverending_nightmare.services.UserService;
import com.example.baehyeonbin.neverending_nightmare.utils.RetrofitUtil;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private boolean isRolling = false;

    private ArrayList<User> userList;
    private String winner;
    private MediaPlayer m;
    private String lastPlayed;

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
                if (isRolling) return;
                isRolling = true;
                pieChart.setRotationAngle(0);
                degree = targetDegree - 346;
                duration = 700;
                deltaDegree = 129;
                deltaDeltaDegree = 3;
                handler.postAtFrontOfQueue(runnable);
                playSound();
                //                t = new Thread(in);
                //                t.start();

            }
        });
        loadData();

    }

    private void setRotationView() {
        handler = new Handler();

        rotationView = findViewById(R.id.rotation_view);
        pieChart = (PieChart) rotationView;
//        s = new String[]{"Green", "Yellow", "Red", "Blue", "HelloHelloHelloHelloHelloHello"};
        List<PieEntry> entries = new ArrayList<>();
        Log.d("winneris", "setRotationView: " + winner);

        for (int i = 0; i < userList.size(); i++) {
            Log.d("ususus", "setRotationView: " + userList.get(i).getName());
            if (userList.get(i).getName().equals(winner)) {
                targetDegree = (float) (i * (360.0 / userList.size()) + (360.0 / userList.size() / 2)) + 180;
            }
            entries.add(new PieEntry((float) (100.0 / userList.size()), userList.get(i).getName()));
        }

        PieDataSet set = new PieDataSet(entries, "사용자 ");
        set.setColors(ColorTemplate.VORDIPLOM_COLORS);
        PieData data = new PieData(set);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh
        pieChart.setCenterText("돌려돌려 돌림판~");
        Description d = new Description();
        d.setText("돌려돌려 돌림판~");
        pieChart.setDescription(d);
        pieChart.setHoleRadius(0);
        pieChart.setUsePercentValues(false);
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
                    Log.d("tada", "target : " + targetDegree + "result: " + degree);
                    return;
                }
                pieChart.setRotationAngle(degree);
                pieChart.invalidate();
                degree += deltaDegree;
                degree = degree % 360;
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

    public void playSound() {
        String word = "rullete";
        try {
            if ((m == null)) {

                m = new MediaPlayer();
            } else if (m != null && lastPlayed.equalsIgnoreCase(word)) {
                m.stop();
                m.release();
                m = null;
                lastPlayed = "";
                return;
            } else if (m != null) {
                m.release();
                m = new MediaPlayer();
            }

            AssetFileDescriptor descriptor = getAssets().openFd(word + ".mp3");
            long start = descriptor.getStartOffset();
            long end = descriptor.getLength();

            // get title
            // songTitle=songsList.get(songIndex).get("songTitle");
            // set the data source
            try {
                m.setDataSource(descriptor.getFileDescriptor(), start, end);
            } catch (Exception e) {
                Log.e("MUSIC SERVICE", "Error setting data source", e);
            }

            m.prepare();
            m.setVolume(1f, 1f);
            // m.setLooping(true);
            m.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        RoomService roomService = RetrofitUtil.INSTANCE.getRetrofit().create(RoomService.class);
        String wallet = getIntent().getStringExtra("wallet");
        Log.d("wallet", wallet);
        Call<WalletRoomResponse> call = roomService.getRandom(new RoomWallet(wallet));
        call.enqueue(new Callback<WalletRoomResponse>() {
            @Override
            public void onResponse(Call<WalletRoomResponse> call, Response<WalletRoomResponse> response) {
                Log.e("code", Integer.toString(response.code()));
                if (response.body() != null) {
                    switch (response.code()) {
                        case 200: {
                            userList = response.body().getMembers();
                            winner = response.body().getName();
                            if (winner != null) {
                                setRotationView();
                            } else {
                                Toast.makeText(RotationActivity.this, "정보를 불러올 수 없습니다", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<WalletRoomResponse> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }

}
