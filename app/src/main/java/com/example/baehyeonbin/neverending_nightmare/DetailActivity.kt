package com.example.baehyeonbin.neverending_nightmare

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import com.example.baehyeonbin.neverending_nightmare.adapter.MoneyAdapter
import com.example.baehyeonbin.neverending_nightmare.beans.*
import com.example.baehyeonbin.neverending_nightmare.services.RoomService
import com.example.baehyeonbin.neverending_nightmare.services.UserService
import com.example.baehyeonbin.neverending_nightmare.utils.RetrofitUtil
import com.example.baehyeonbin.neverending_nightmare.utils.SharedPreferenceUtil
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity(){

    val money : ArrayList<MoneyItem> = ArrayList()

    companion object {
        val TAG : String = DetailActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(detail_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        detail_toolbar.overflowIcon?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)

        loadData()
    }

    fun loadData() {
        var userService : UserService = RetrofitUtil.retrofit.create(UserService::class.java)
        var call = userService.getUserProfile(Wallet(SharedPreferenceUtil.getPreference(applicationContext, "server_wallet")!!))
        call.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e(TAG, t.toString())
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.e("code" ,response.code().toString())
                if(response.body() != null) {
                    when(response.code()) {
                        200 -> {
                            money.clear()
                            money.addAll(response.body()!!.enteredWallet!!)
                            setRecyclerView()
                        }
                    }
                }
            }

        })
    }

    fun setRecyclerView() {
        money_list.layoutManager = GridLayoutManager(this, 2)
        money_list.adapter = MoneyAdapter(money, this)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.overflow_create -> {
                createDialog()
            }

            R.id.overflow_join -> {
                joinDialog()
            }

            R.id.overflow_mypage -> {
                var intent = Intent(this@DetailActivity, MypageActivity::class.java)
                startActivity(intent)
            }

            R.id.overflow_log -> {
                var intent = Intent(this@DetailActivity, LogActivity::class.java)
                startActivity(intent)
            }
        }

        return false
    }

    fun createDialog(){
        val builder = AlertDialog.Builder(this@DetailActivity)
        builder.setTitle("모크돈 개설하기")
        builder.setMessage("모크돈 이름을 입력해주세요")

        val edittext = EditText(this@DetailActivity)
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        edittext.layoutParams = lp
        builder.setView(edittext)

        builder.setPositiveButton("Create", DialogInterface.OnClickListener { dialog, which ->
            var roomService : RoomService = RetrofitUtil.retrofit.create(RoomService::class.java)
            var name = SharedPreferenceUtil.getPreference(applicationContext, "name")!!
            var wallet = SharedPreferenceUtil.getPreference(applicationContext, "server_wallet")!!
            Log.d("asdf", "name is $name, wallet is $wallet")
            var call = roomService.createRoom(CreateRoom(edittext.text.toString(), wallet, name ))

            call.enqueue(object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e(TAG, t.toString())
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if(response.body() != null) {
                        when(response.code()) {
                            200 -> {
                                money.clear()
                                money.addAll(response.body()!!.enteredWallet!!)
                                setRecyclerView()
                            }
                        }
                    }
                }

            })
            dialog.dismiss()
        })

        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
            toast("Cancel")
            dialog.dismiss()
        })

        builder.show()
    }

    fun joinDialog(){
        val builder = AlertDialog.Builder(this@DetailActivity)
        builder.setTitle("모크돈 참가하기")
        builder.setMessage("주소를 입력해주세요")

        val edittext = EditText(this@DetailActivity)
        builder.setView(edittext)

        builder.setPositiveButton("Join") { dialog, which ->
            var roomService : RoomService = RetrofitUtil.retrofit.create(RoomService::class.java)
            var name = SharedPreferenceUtil.getPreference(applicationContext, "name")!!
            var wallet = SharedPreferenceUtil.getPreference(applicationContext, "server_wallet")!!
            var call = roomService.joinRoom(JoinRoom(name, wallet, edittext.text.toString()))
            call.enqueue(object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e(TAG, t.toString())
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    Log.e("detail http code", response.code().toString())
                    if(response.body() != null) {
                        when(response.code()) {
                            200 -> {
                                money.clear()
                                money.addAll(response.body()!!.enteredWallet!!)
                                setRecyclerView()
                                dialog.dismiss()
                            }
                        }
                    }
                }

            })
        }

        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
            toast("Cancel")
            dialog.dismiss()
        })

        builder.show()
    }

}