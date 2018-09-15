package com.example.baehyeonbin.neverending_nightmare

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.baehyeonbin.neverending_nightmare.beans.User
import com.example.baehyeonbin.neverending_nightmare.beans.Wallet
import com.example.baehyeonbin.neverending_nightmare.services.UserService
import com.example.baehyeonbin.neverending_nightmare.utils.RetrofitUtil
import com.example.baehyeonbin.neverending_nightmare.utils.SharedPreferenceUtil
import kotlinx.android.synthetic.main.activity_mypage.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        loadData()
        setListeners()
    }

    fun loadData() {
        var userService : UserService = RetrofitUtil.retrofit.create(UserService::class.java)
        var call = userService.getUserProfile(Wallet(SharedPreferenceUtil.getPreference(applicationContext, "server_wallet")!!))
        call.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e(DetailActivity.TAG, t.toString())
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.e("mypage code", response.code().toString())
                if(response.body() != null) {
                    when(response.code()) {
                        200 -> {
                            name.text = response.body()!!.name
                            hyc.text = "${response.body()!!.balance}HYC"
                            krw.text = "${(response.body()!!.balance * 25)}KRW"
                        }
                    }
                }
            }

        })

    }

    fun setListeners() {
        logoutButton.setOnClickListener {
            SharedPreferenceUtil.removePreferences(applicationContext, "name")
            SharedPreferenceUtil.removePreferences(applicationContext, "server_wallet")
            SharedPreferenceUtil.removePreferences(applicationContext, "server_private_key")
            SharedPreferenceUtil.removePreferences(applicationContext, "server_mnemonic")

            startActivity(Intent(this@MypageActivity, RegisterOrLoginActivity::class.java))
        }
    }
}
