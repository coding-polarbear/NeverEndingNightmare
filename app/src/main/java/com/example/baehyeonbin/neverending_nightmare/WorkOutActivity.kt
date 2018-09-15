package com.example.baehyeonbin.neverending_nightmare

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.baehyeonbin.neverending_nightmare.beans.MoneyItem
import kotlinx.android.synthetic.main.activity_work_out.*

class WorkOutActivity : AppCompatActivity() {

    lateinit var item : MoneyItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_out)

        item = intent.getSerializableExtra("item") as MoneyItem
        init()
    }

    fun init() {
        name.text = item.key
    }
}
