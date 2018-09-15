package com.example.baehyeonbin.neverending_nightmare.beans

import com.google.gson.annotations.SerializedName

data class JoinRoom(val name : String,
                    val wallet : String,
                    @SerializedName("room_wallet") val roomWallet : String)