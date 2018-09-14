package com.example.baehyeonbin.neverending_nightmare.beans

import com.google.gson.annotations.SerializedName

data class Room(@SerializedName("server_wallet") val serverWallet : String,
                @SerializedName("server_private_key") val serverPrivateKey : String,
                @SerializedName("server_mnemonic") val serverMnemonic : String,
                @SerializedName("selected_person") val selectedPerson : String,
                val members : ArrayList<User>,
                @SerializedName("member_extended_seed") val memberExtendedSeed : ArrayList<User> )