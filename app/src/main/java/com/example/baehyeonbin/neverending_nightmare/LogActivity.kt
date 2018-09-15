package com.example.baehyeonbin.neverending_nightmare

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.baehyeonbin.neverending_nightmare.adapter.LogAdapter
import com.example.baehyeonbin.neverending_nightmare.beans.LogData
import com.example.baehyeonbin.neverending_nightmare.beans.TXS
import com.example.baehyeonbin.neverending_nightmare.beans.TXSResponse
import com.example.baehyeonbin.neverending_nightmare.services.HyconService
import com.example.baehyeonbin.neverending_nightmare.utils.RetrofitUtil
import com.example.baehyeonbin.neverending_nightmare.utils.SharedPreferenceUtil
import kotlinx.android.synthetic.main.activity_log.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.support.v7.widget.DividerItemDecoration



class LogActivity : AppCompatActivity() {

    companion object {
        val TAG: String = LogActivity::class.java.simpleName
    }

    val logs : ArrayList<TXS> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        loadData()
    }

    fun loadData() {
        var hyconService : HyconService = RetrofitUtil.hyconRetrofit.create(HyconService::class.java)
        var call = hyconService.getTransaction(SharedPreferenceUtil.getPreference(applicationContext, "server_wallet")!!)
        call.enqueue(object : Callback<TXSResponse> {
            override fun onFailure(call: Call<TXSResponse>, t: Throwable) {
                Log.e(TAG, t.toString())
            }

            override fun onResponse(call: Call<TXSResponse>, response: Response<TXSResponse>) {
                Log.d("code", response.code().toString())
                if(response.body() != null) {
                    when(response.code()) {
                        200 -> {
                            logs.clear()
                            logs.addAll(response.body()!!.txs)
                            setRecyclerView()
                        }
                    }
                }
            }

        })
    }

    fun setRecyclerView() {
        log_list.layoutManager = LinearLayoutManager(this@LogActivity)
        log_list.adapter = LogAdapter(logs, this)
        log_list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

    }

//    fun setLogs(){
//        logs.add(LogData("from1", "to1", "fee1", "amount1"))
//        logs.add(LogData("from2", "to2", "fee2", "amount2"))
//        logs.add(LogData("from3", "to3", "fee3", "amount3"))
//        logs.add(LogData("from4", "to4", "fee4", "amount4"))
//    }
}