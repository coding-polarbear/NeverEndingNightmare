package com.example.baehyeonbin.neverending_nightmare.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.baehyeonbin.neverending_nightmare.BlankActivity
import com.example.baehyeonbin.neverending_nightmare.R
import com.example.baehyeonbin.neverending_nightmare.data.MoneyItem
import kotlinx.android.synthetic.main.money_list_item.view.*
import org.jetbrains.anko.toast

class MoneyAdapter(val items : ArrayList<MoneyItem>, val context : Context) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.money_list_item, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        holder.money_item.text = items[p1].money + "\n" + items[p1].key
        holder.money_item.setOnClickListener {
            context.toast(holder.money_item.text)
            context.startActivity(Intent(context, BlankActivity::class.java))
        }
    }
}

class ViewHolder(val view : View) : RecyclerView.ViewHolder(view){
    val money_item = view.money_item
}