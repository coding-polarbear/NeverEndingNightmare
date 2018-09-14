package com.example.baehyeonbin.neverending_nightmare.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageButton
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(url : Uri, context: Context){//수정 할 때
    Glide.with(context).load(url).apply(RequestOptions().centerCrop()).into(this)
}

fun ImageView.loadUrl(url : String) {
    Glide.with(context).load(url).into(this)
}

fun ImageButton.loadImageFromUrl(url : String) {
    Glide.with(context).load(url).into(this)
}