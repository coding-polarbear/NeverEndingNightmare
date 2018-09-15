package com.example.baehyeonbin.neverending_nightmare

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_moneysend.*

class MoneySendActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moneysend)

        send_edittext.setText("SEND")
    }
}