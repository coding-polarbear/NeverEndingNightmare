package com.example.baehyeonbin.neverending_nightmare

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.acrivity_moneyback.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class MoneyBackActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acrivity_moneyback)

        back_button.setOnClickListener {
            back_edittext.setText("BACK")
        }
    }
}