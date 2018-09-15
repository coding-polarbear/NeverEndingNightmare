package com.example.baehyeonbin.neverending_nightmare.services

import com.example.baehyeonbin.neverending_nightmare.beans.TXSResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HyconService {
    @GET("wallet/{roomAddress}/txs")
    fun getTransaction(@Path("roomAddress") roomAddress : String) : Call<TXSResponse>
}