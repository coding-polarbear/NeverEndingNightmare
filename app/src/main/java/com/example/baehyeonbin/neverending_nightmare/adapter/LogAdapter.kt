package com.example.baehyeonbin.neverending_nightmare.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.baehyeonbin.neverending_nightmare.R
import com.example.baehyeonbin.neverending_nightmare.beans.LogData
import com.example.baehyeonbin.neverending_nightmare.beans.TXS
import kotlinx.android.synthetic.main.content_log.view.*

class LogAdapter(val items : ArrayList<TXS>, val context : Context) : RecyclerView.Adapter<ViewHolderLog>(){
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolderLog {
        val layoutInflater = LayoutInflater.from(context).inflate(R.layout.content_log, parent, false)

        return ViewHolderLog(layoutInflater)
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolderLog, pos: Int) {
        var text : String = "from : ${items[pos].from}\nto : ${items[pos].to}\nfee : ${items[pos].fee}\namount : ${items[pos].amount}"
        holder.textView.text = text
    }
}

class ViewHolderLog(view : View) : RecyclerView.ViewHolder(view){
    val textView = view.log_list_item
}