package com.example.baehyeonbin.neverending_nightmare

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_nickname.*
import org.jetbrains.anko.toast

class NickNameActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nickname)

        lateinit var nickname : String
        nickname_button.setOnClickListener {
                nickname = nickname_edittext.text.toString()
//            toast(nickname)

            var intent = Intent(this@NickNameActivity, DetailActivity::class.java)
            startActivity(intent)
        }
    }
}