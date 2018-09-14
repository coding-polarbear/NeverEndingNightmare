package com.example.baehyeonbin.neverending_nightmare

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.example.baehyeonbin.neverending_nightmare.adapter.MoneyAdapter
import com.example.baehyeonbin.neverending_nightmare.data.MoneyItem
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(){

    val money : ArrayList<MoneyItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setMoney()
        money_list.layoutManager = GridLayoutManager(this, 3)
        money_list.adapter = MoneyAdapter(money, this)
    }

    fun setMoney(){
        money.add(MoneyItem("AAA"))
        money.add(MoneyItem("BBB"))
        money.add(MoneyItem("CCC"))
        money.add(MoneyItem("DDD"))
        money.add(MoneyItem("EEE"))
        money.add(MoneyItem("FFF"))
        money.add(MoneyItem("GGG"))
    }
}