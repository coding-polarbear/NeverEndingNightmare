package com.example.baehyeonbin.neverending_nightmare.beans

data class WalletRoomResponse(val name : String,
                              val wallet : String,
                              val members : ArrayList<User>)