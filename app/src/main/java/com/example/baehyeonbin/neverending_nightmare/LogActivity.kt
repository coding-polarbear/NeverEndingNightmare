package com.example.baehyeonbin.neverending_nightmare

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.baehyeonbin.neverending_nightmare.adapter.LogAdapter
import com.example.baehyeonbin.neverending_nightmare.beans.LogData
import kotlinx.android.synthetic.main.activity_log.*

class LogActivity : AppCompatActivity() {

    val logs : ArrayList<LogData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        setLogs()
        log_list.layoutManager = LinearLayoutManager(this@LogActivity)
        log_list.adapter = LogAdapter(logs, this)
    }

    fun setLogs(){
        logs.add(LogData("from1", "to1", "fee1", "amount1"))
        logs.add(LogData("from2", "to2", "fee2", "amount2"))
        logs.add(LogData("from3", "to3", "fee3", "amount3"))
        logs.add(LogData("from4", "to4", "fee4", "amount4"))
    }
}