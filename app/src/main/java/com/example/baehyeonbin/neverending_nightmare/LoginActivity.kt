package com.example.baehyeonbin.neverending_nightmare

import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.baehyeonbin.neverending_nightmare.utils.SharedPreferenceUtil
import kotlinx.android.synthetic.main.activity_load_wallet.*
import org.apache.commons.io.FileUtils
import org.jetbrains.anko.toast
import org.json.JSONObject
import pub.devrel.easypermissions.EasyPermissions
import java.io.File

class LoginActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks{

    private val PICKFILE_RESULT_CODE = 1000

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(requestCode == 300) {
            EasyPermissions.requestPermissions(this, "파일을 가져오기 위해서 권한이 필요합니다", 300, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        requestFile()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_wallet)

        setListeners()
    }

    fun setListeners() {
        key_button.setOnClickListener {
            if (EasyPermissions.hasPermissions(applicationContext, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                requestFile()
            } else {
                EasyPermissions.requestPermissions(this, "파일을 가져오기 위해서 권한이 필요합니다", 300, android.Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }

    private fun requestFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(Intent.createChooser(intent,
                "파일을 선택해주세요!"), PICKFILE_RESULT_CODE)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun getPath(context: Context, uri: Uri): String? {

        val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]

                if ("primary".equals(type, ignoreCase = true)) {
                    return "${Environment.getExternalStorageDirectory()}/${split[1]}"
                }

                // TODO handle non-primary volumes
            } else if (isDownloadsDocument(uri)) {

                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id))

                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]

                var contentUri: Uri? = null
                if ("image" == type) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }

                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])

                return getDataColumn(context, contentUri!!, selection, selectionArgs)
            }// MediaProvider
            // DownloadsProvider
        } else if ("content".equals(uri.getScheme(), ignoreCase = true)) {
            return getDataColumn(context, uri, null, null)
        } else if ("file".equals(uri.getScheme(), ignoreCase = true)) {
            return uri.getPath()
        }// File
        // MediaStore (and general)

        return null
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    fun getDataColumn(context: Context, uri: Uri?, selection: String?,
                      selectionArgs: Array<String>?): String? {

        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)

        try {
            cursor = context.contentResolver.query(uri, projection, selection, selectionArgs, null)
            if (cursor != null && cursor!!.moveToFirst()) {
                val column_index = cursor!!.getColumnIndexOrThrow(column)
                return cursor!!.getString(column_index)
            }
        } finally {
            if (cursor != null)
                cursor!!.close()
        }
        return null
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICKFILE_RESULT_CODE) {
            if (EasyPermissions.hasPermissions(applicationContext, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                try {
                    val uri = data!!.data ?: throw NullPointerException("I hate this")
                    val path = getPath(this, uri)
                    val file = File(path)
                    val s = FileUtils.readFileToString(file, "UTF-8")
                    Log.d("json", s)
                    var jsonObject : JSONObject = JSONObject(s)
                    var value = ""
                    value += "server_wallet : ${jsonObject.getString("server_wallet")}"
                    SharedPreferenceUtil.savePreferences(applicationContext, "name", jsonObject.getString("name"))
                    SharedPreferenceUtil.savePreferences(applicationContext, "server_wallet", jsonObject.getString("server_wallet"))
                    SharedPreferenceUtil.savePreferences(applicationContext, "server_private_key", jsonObject.getString("server_private_key"))
                    SharedPreferenceUtil.savePreferences(applicationContext, "server_mnemonic", jsonObject.getString("server_mnemonic"))
                    Log.d("good_face", uri.path)
                    toast("성공적으로 지갑 데이터를 가져왔습니다!")

                    var intent = Intent(this@LoginActivity, DetailActivity::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "파일을 불러오는데 문제가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                }

            } else {
                EasyPermissions.requestPermissions(this,
                        "ex파일을 가져오기 위해서 권한이 필요합니다", 300, android.Manifest.permission.READ_EXTERNAL_STORAGE)

            }
        }
    }
}