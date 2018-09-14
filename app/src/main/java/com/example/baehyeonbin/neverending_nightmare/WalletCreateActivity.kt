package com.example.baehyeonbin.neverending_nightmare

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.baehyeonbin.neverending_nightmare.beans.Room
import com.example.baehyeonbin.neverending_nightmare.services.UserService
import com.example.baehyeonbin.neverending_nightmare.utils.RetrofitUtil
import com.example.baehyeonbin.neverending_nightmare.utils.SharedPreferenceUtil
import kotlinx.android.synthetic.main.activity_wallet.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WalletCreateActivity : AppCompatActivity(){
    companion object {
        val TAG  : String = WalletCreateActivity::class.java.simpleName
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)
        setListeners()
    }

    fun setListeners() {
        wallet_button.setOnClickListener {
            createWallet()
        }
    }

    fun createWallet() {
        var userService = RetrofitUtil.retrofit.create(UserService::class.java)
        var call = userService.generateWallet()
        call.enqueue(object : Callback<Room> {
            override fun onFailure(call: Call<Room>, t: Throwable) {
                Log.e(TAG, t.toString() )
            }

            override fun onResponse(call: Call<Room>, response: Response<Room>) {
                Log.d(TAG, response.code().toString())
                if(response.body() != null) {
                    when(response.code()) {
                        200 -> {
                            toast("성공적으로 지갑이 생성되었습니다!")
                            SharedPreferenceUtil.savePreferences(applicationContext, "server_wallet", response.body()!!.serverWallet)
                            SharedPreferenceUtil.savePreferences(applicationContext, "server_private_key", response.body()!!.serverPrivateKey)
                            SharedPreferenceUtil.savePreferences(applicationContext, "server_mnemonic", response.body()!!.serverMnemonic)
                            var newIntent = Intent(this@WalletCreateActivity, NickNameActivity::class.java)
                            newIntent.putExtra("wallet", response.body()!!.serverWallet)
                            startActivity(newIntent)
                        }
                        else -> {
                            Log.e(TAG, response.code().toString())
                        }
                    }
                }
            }

        })
    }
}