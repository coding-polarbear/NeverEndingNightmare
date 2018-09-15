package com.example.baehyeonbin.neverending_nightmare.beans

import com.google.gson.annotations.SerializedName

data class CreateRoom (@SerializedName("wallet_name") val walletName : String,
                       @SerializedName("opener_wallet") val openerWallet : String,
                       @SerializedName("opener_name") val openerName : String)