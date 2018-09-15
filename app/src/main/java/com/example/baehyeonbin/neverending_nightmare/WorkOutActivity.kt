package com.example.baehyeonbin.neverending_nightmare

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.baehyeonbin.neverending_nightmare.beans.DetailRoom
import com.example.baehyeonbin.neverending_nightmare.beans.MoneyItem
import com.example.baehyeonbin.neverending_nightmare.services.RoomService
import com.example.baehyeonbin.neverending_nightmare.utils.RetrofitUtil
import kotlinx.android.synthetic.main.activity_work_out.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkOutActivity : AppCompatActivity() {

    lateinit var item : MoneyItem

    companion object {
        val TAG : String = WorkOutActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_out)

        item = intent.getSerializableExtra("item") as MoneyItem
        init()
        loadData()
        setListener()
    }

    fun setListener(){
        sendMoney.setOnClickListener {
            var intent = Intent(this@WorkOutActivity, MoneySendActivity::class.java)
            startActivity(intent)
        }

        receiveMoney.setOnClickListener {
            var intent = Intent(this@WorkOutActivity, MoneyBackActivity::class.java)
            startActivity(intent)
        }
    }

    fun init() {
        name.text = item.key

        lottoButton.setOnClickListener {
            var newIntent = Intent(this@WorkOutActivity, RotationActivity::class.java)
            newIntent.putExtra("wallet", item.wallet)
            startActivity(newIntent)
        }
    }

    fun loadData() {
        var roomService : RoomService = RetrofitUtil.retrofit.create(RoomService::class.java)
        var call = roomService.getRoom(item.wallet)
        call.enqueue(object : Callback<DetailRoom> {
            override fun onFailure(call: Call<DetailRoom>, t: Throwable) {
                Log.e(TAG, t.toString())
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<DetailRoom>, response: Response<DetailRoom>) {
                if(response.body() != null) {
                    when(response.code()) {
                        200 -> {
                            name.text = item.key
                            var size = response.body()!!.result.members.size
                            peopleNum.text = "${size}명"
                            competition.text = "$size : 1"
                        }
                    }
                }
            }

        })
    }
}
