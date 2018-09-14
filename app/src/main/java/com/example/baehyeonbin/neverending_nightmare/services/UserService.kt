package com.example.baehyeonbin.neverending_nightmare.services

import com.example.baehyeonbin.neverending_nightmare.beans.Result
import com.example.baehyeonbin.neverending_nightmare.beans.Room
import com.example.baehyeonbin.neverending_nightmare.beans.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("user/generate_wallet")
    fun generateWallet() : Call<Room>

    @POST("user/register")
    fun register(@Body user : User) : Call<Result>
}