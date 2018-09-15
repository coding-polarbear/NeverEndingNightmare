package com.example.baehyeonbin.neverending_nightmare.beans

data class TXS(val hash : String , val amount : String, val fee : String, val from : String, val to : String, val estimated : String, val receiveTime : String)

data class TXSResponse(var txs : ArrayList<TXS>)