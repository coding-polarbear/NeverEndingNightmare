package com.example.baehyeonbin.neverending_nightmare

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.baehyeonbin.neverending_nightmare.beans.Room
import com.example.baehyeonbin.neverending_nightmare.beans.SendRequest
import com.example.baehyeonbin.neverending_nightmare.services.RoomService
import com.example.baehyeonbin.neverending_nightmare.utils.RetrofitUtil
import com.example.baehyeonbin.neverending_nightmare.utils.SharedPreferenceUtil
import kotlinx.android.synthetic.main.activity_moneysend.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.PrivateKey

class MoneySendActivity : AppCompatActivity(){

    var amount : Int = 0
    lateinit var roomWallet : String
    lateinit var privateKey: String
    lateinit var myWallet : String

    companion object {
        val TAG : String = MoneySendActivity::class.java.simpleName
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moneysend)

        roomWallet = intent.getStringExtra("wallet")
        privateKey = SharedPreferenceUtil.getPreference(applicationContext, "server_private_key")!!
        myWallet = SharedPreferenceUtil.getPreference(applicationContext, "server_wallet")!!

        setListeners()
    }

    fun setListeners() {
        send_button.setOnClickListener {
            var roomService = RetrofitUtil.retrofit.create(RoomService::class.java)
            var call = roomService.sendCoin(SendRequest(privateKey, myWallet, roomWallet, send_edittext.text.toString().toDouble()))
            call.enqueue(object : Callback<Room> {
                override fun onFailure(call: Call<Room>, t: Throwable) {
                    Log.e(TAG, t.toString())
                }

                override fun onResponse(call: Call<Room>, response: Response<Room>) {
                    if(response.body() != null) {
                        when(response.code()) {
                            200 -> {
                                toast("공동 계좌에 ${send_edittext.text.toString().toDouble()} 만큼의 Hycon을 보냈습니다!")
                                finish()
                            }
                            else -> {
                                toast("Network Error!")
                            }
                        }
                    }
                }

            })
        }
    }
}