package com.example.baehyeonbin.neverending_nightmare.services

import com.example.baehyeonbin.neverending_nightmare.beans.Room
import retrofit2.Call
import retrofit2.http.POST

interface UserService {
    @POST("user/generate_wallet")
    fun generateWallet() : Call<Room>
}