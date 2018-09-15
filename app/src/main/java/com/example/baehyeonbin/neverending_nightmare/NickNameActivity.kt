package com.example.baehyeonbin.neverending_nightmare

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.baehyeonbin.neverending_nightmare.beans.Result
import com.example.baehyeonbin.neverending_nightmare.beans.User
import com.example.baehyeonbin.neverending_nightmare.services.UserService
import com.example.baehyeonbin.neverending_nightmare.utils.RetrofitUtil
import com.example.baehyeonbin.neverending_nightmare.utils.SharedPreferenceUtil
import kotlinx.android.synthetic.main.activity_nickname.*
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast
import android.os.Environment.getExternalStorageDirectory
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class NickNameActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
            EasyPermissions.requestPermissions(this, "파일을 저장하기 위해서 권한이 필요합니다", 400, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        saveFile(data, name)
    }

    lateinit var nickname : String
    lateinit var name : String
    lateinit var data : String

    companion object {
        val TAG : String = NickNameActivity::class.java.simpleName
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nickname)

        setListeners()
    }

    fun setListeners() {
        nickname_button.setOnClickListener {
            register()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    fun register() {
        var userService = RetrofitUtil.retrofit.create(UserService::class.java)
        var call = userService.register(User(intent.getStringExtra("wallet"), nickname_edittext.text.toString(), 0.0, null))
        call.enqueue(object : Callback<Result> {
            override fun onFailure(call: Call<Result>, t: Throwable) {
                Log.e(TAG, t.toString())
            }

            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                Log.e(TAG, response.code().toString())
                if(response.body() != null) {
                    when(response.code()) {
                        200 -> {
                            toast(response.body()!!.message!!)
                            var serverWallet = SharedPreferenceUtil.getPreference(applicationContext, "server_wallet")!!
                            var serverPrivateKey = SharedPreferenceUtil.getPreference(applicationContext, "server_private_key")!!
                            var serverMnemonic = SharedPreferenceUtil.getPreference(applicationContext, "server_mnemonic")!!
                            SharedPreferenceUtil.savePreferences(applicationContext, "name", nickname_edittext.text.toString())
                            var jsonObject : JSONObject = JSONObject()
                            jsonObject.put("name", SharedPreferenceUtil.getPreference(applicationContext, "name"))
                            jsonObject.put("server_wallet", serverWallet)
                            jsonObject.put("server_private_key", serverPrivateKey)
                            jsonObject.put("server_mnemonic", serverMnemonic)
                            data = jsonObject.toString()
                            name = ""

                            if (EasyPermissions.hasPermissions(applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                    saveFile(jsonObject.toString(), "")
                                    var intent = Intent(this@NickNameActivity, DetailActivity::class.java)
                                    startActivity(intent)
                            } else {
                                Toast.makeText(applicationContext, "Fuck", Toast.LENGTH_LONG).show()
                                EasyPermissions.requestPermissions(this@NickNameActivity, "파일을 저장하기 위해서 권한이 필요합니다", 400, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            }
                        }
                        else -> {
                            val message = response.body()!!.message
                            if(message != null) {
                                toast(message)
                            }
                        }
                    }
                }
            }

        })
    }

    fun checkPermission(receivedData : String, saveName : String) {
        if(EasyPermissions.hasPermissions(applicationContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            saveFile(data, name)
        } else {
            EasyPermissions.requestPermissions(this, "파일을 저장하기 위해서 권한이 필요합니다", 400, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, "확인")
        }

    }

    private fun saveFile(data : String, name : String) {
        Log.e("asdF", "asdf")
        try {
            val file = File(Environment.getExternalStorageDirectory().toString() + "/result.key")
            val fos = FileOutputStream(file)
            fos.write(data.toByteArray())
            fos.close()
            Toast.makeText(applicationContext, "성공적으로 저장했습니다!", Toast.LENGTH_SHORT).show()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

}