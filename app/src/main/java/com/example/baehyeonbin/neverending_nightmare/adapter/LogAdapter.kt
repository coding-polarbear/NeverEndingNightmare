package com.example.baehyeonbin.neverending_nightmare.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.baehyeonbin.neverending_nightmare.R
import com.example.baehyeonbin.neverending_nightmare.beans.LogData
import kotlinx.android.synthetic.main.content_log.view.*

class LogAdapter(val items : ArrayList<LogData>, val context : Context) : RecyclerView.Adapter<ViewHolderLog>(){
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolderLog {
        val layoutInflater = LayoutInflater.from(context).inflate(R.layout.content_log, parent, false)

        return ViewHolderLog(layoutInflater)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolderLog, pos: Int) {
        holder.textView.text = items[pos].from + ", " + items[pos].to + ", " + items[pos].fee + ", " + items[pos].amount
    }
}

class ViewHolderLog(view : View) : RecyclerView.ViewHolder(view){
    val textView = view.log_list_item
}