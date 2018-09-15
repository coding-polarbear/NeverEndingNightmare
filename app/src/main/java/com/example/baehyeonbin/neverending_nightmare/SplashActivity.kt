package com.example.baehyeonbin.neverending_nightmare

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.baehyeonbin.neverending_nightmare.utils.SharedPreferenceUtil

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Thread(Runnable {
            Thread.sleep(1500)

            val serverWallet = SharedPreferenceUtil.getPreference(applicationContext, "server_wallet")
            val serverPrivateKey = SharedPreferenceUtil.getPreference(applicationContext, "server_private_key")
            val serverMnemonic = SharedPreferenceUtil.getPreference(applicationContext, "server_mnemonic")

            if (serverWallet != null && serverPrivateKey != null && serverMnemonic != null) {
                val intent = Intent(this@SplashActivity, DetailActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this@SplashActivity, RegisterOrLoginActivity::class.java)
//                 val intent = Intent(this@SplashActivity, DetailActivity::class.java)
                startActivity(intent)
            }

            finish()
        }).start()
    }
}
