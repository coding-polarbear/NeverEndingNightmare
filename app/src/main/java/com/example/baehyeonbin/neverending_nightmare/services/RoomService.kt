package com.example.baehyeonbin.neverending_nightmare.services

import com.example.baehyeonbin.neverending_nightmare.beans.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RoomService {
    @POST("room/enter")
    fun joinRoom(@Body joinRoom : JoinRoom) : Call<User>

    @POST("room/create")
    fun createRoom(@Body createRoom : CreateRoom) : Call<User>

    @POST("room/get_random")
    fun getRandom(@Body roomWallet : RoomWallet) : Call<WalletRoomResponse>
}