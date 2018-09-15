package com.example.baehyeonbin.neverending_nightmare.beans

import com.google.gson.annotations.SerializedName

data class User (val name : String,
                 val wallet : String,
                 @SerializedName("entered_wallet") val enteredWallet : ArrayList<MoneyItem>?)