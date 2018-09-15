package com.example.baehyeonbin.neverending_nightmare.beans

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MoneyItem(@SerializedName("wallet_name") val key : String,
                     @SerializedName("server_wallet") val wallet : String,
                     val money : String) : Serializable