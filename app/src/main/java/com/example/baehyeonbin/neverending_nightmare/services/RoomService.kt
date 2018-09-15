package com.example.baehyeonbin.neverending_nightmare.services

import com.example.baehyeonbin.neverending_nightmare.beans.CreateRoom
import com.example.baehyeonbin.neverending_nightmare.beans.JoinRoom
import com.example.baehyeonbin.neverending_nightmare.beans.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RoomService {
    @POST("room/enter")
    fun joinRoom(@Body joinRoom : JoinRoom) : Call<User>

    @POST("room/create")
    fun createRoom(@Body createRoom : CreateRoom) : Call<User>
}