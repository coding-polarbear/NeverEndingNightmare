package com.example.baehyeonbin.neverending_nightmare

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.baehyeonbin.neverending_nightmare.beans.ReceiveRequest
import com.example.baehyeonbin.neverending_nightmare.beans.ReceiveResponse
import com.example.baehyeonbin.neverending_nightmare.services.RoomService
import com.example.baehyeonbin.neverending_nightmare.utils.RetrofitUtil
import com.example.baehyeonbin.neverending_nightmare.utils.SharedPreferenceUtil
import kotlinx.android.synthetic.main.acrivity_moneyback.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoneyBackActivity : AppCompatActivity(){

    companion object {
        val TAG : String = MoneyBackActivity::class.java.simpleName
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acrivity_moneyback)
//        var amount = intent.getIntExtra("amount", 0)
//        var wallet = intent.getStringExtra("wallet")
//        back_edittext.setText(intent.getIntExtra("amount", 0).toString())
//        back_button.setOnClickListener {
//            var roomService = RetrofitUtil.retrofit.create(RoomService::class.java)
//            var to = SharedPreferenceUtil.getPreference(applicationContext, "server_wallet")!!
//            var call = roomService.receiveCoin(ReceiveRequest(to, amount.toDouble(), wallet))
//            call.enqueue(object : Callback<ReceiveResponse> {
//                override fun onFailure(call: Call<ReceiveResponse>, t: Throwable) {
//                    Log.e(TAG, t.toString())
//                }
//
//                override fun onResponse(call: Call<ReceiveResponse>, response: Response<ReceiveResponse>) {
//                    if(response.body() != null) {
//                        when(response.code()) {
//                            200 -> {
//                                toast("성공적으로 받았습니다!")
//                                finish()
//                            }
//                        }
//                    }
//                }
//
//            })
//        }
    }
}