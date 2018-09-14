package com.example.baehyeonbin.neverending_nightmare

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_registerorlogin.*

class RegisterOrLoginActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registerorlogin)

        register_button.setOnClickListener {
            val intent = Intent(this@RegisterOrLoginActivity, WalletCreateActivity::class.java)
            startActivity(intent)
        }

        login_button.setOnClickListener {
            val intent = Intent(this@RegisterOrLoginActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}