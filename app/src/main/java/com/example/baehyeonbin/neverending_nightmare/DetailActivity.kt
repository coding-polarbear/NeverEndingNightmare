package com.example.baehyeonbin.neverending_nightmare

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import com.example.baehyeonbin.neverending_nightmare.adapter.MoneyAdapter
import com.example.baehyeonbin.neverending_nightmare.data.MoneyItem
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import org.jetbrains.anko.toast

class DetailActivity : AppCompatActivity(){

    val money : ArrayList<MoneyItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(detail_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        detail_toolbar.overflowIcon?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)

        setMoney()
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
                var intent = Intent(this@DetailActivity, BlankActivity::class.java)
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
            toast("Create")
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

        builder.setPositiveButton("Create", DialogInterface.OnClickListener { dialog, which ->
            toast("Join")
            dialog.dismiss()
        })

        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
            toast("Cancel")
            dialog.dismiss()
        })

        builder.show()
    }

    fun setMoney(){
        money.add(MoneyItem("원준이네", "620HYC/1000HYC"))
        money.add(MoneyItem("코와이네", "500HYC/500HYC"))
        money.add(MoneyItem("원준이네", "620HYC/1000HYC"))
        money.add(MoneyItem("코와이네", "500HYC/500HYC"))
    }
}