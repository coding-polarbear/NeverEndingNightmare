package com.example.baehyeonbin.neverending_nightmare.beans

data class ReceiveRequest(val to : String, val amount : Double, val room_wallet : String)

data class ReceiveResponse(val txHash : String )