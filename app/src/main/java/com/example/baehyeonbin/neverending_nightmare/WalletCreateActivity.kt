package com.example.baehyeonbin.neverending_nightmare

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_wallet.*
import org.jetbrains.anko.toast

class WalletCreateActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

        wallet_button.setOnClickListener {
            toast("wallet created!")

            var intent = Intent(this@WalletCreateActivity, NickNameActivity::class.java)
            startActivity(intent)
        }
    }
}