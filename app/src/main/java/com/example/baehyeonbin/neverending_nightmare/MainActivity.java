package com.example.baehyeonbin.neverending_nightmare;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.baehyeonbin.neverending_nightmare.utils.SharedPreferenceUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                String serverWallet = SharedPreferenceUtil.INSTANCE.getPreference(getApplicationContext(), "server_wallet");
                String serverPrivateKey = SharedPreferenceUtil.INSTANCE.getPreference(getApplicationContext(), "server_private_key");
                String serverMnemonic = SharedPreferenceUtil.INSTANCE.getPreference(getApplicationContext(), "server_mnemonic");

                if(serverWallet != null && serverPrivateKey != null && serverMnemonic != null) {
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, RegisterOrLoginActivity.class);
//                  Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    startActivity(intent);
                }


            }
        });
//        startActivity(new Intent(this, RotationActivity.class));

    }

}
