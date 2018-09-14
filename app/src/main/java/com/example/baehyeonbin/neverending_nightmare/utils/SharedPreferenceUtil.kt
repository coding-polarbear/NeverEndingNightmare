package com.example.baehyeonbin.neverending_nightmare.utils

import android.content.Context

object SharedPreferenceUtil {
    fun getPreference(context: Context, key: String): String? {
        val sharedPreferences = context.getSharedPreferences("hycon", Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, null)
    }

    fun savePreferences(context: Context, key: String, data: String) {
        val sharedPreferences = context.getSharedPreferences("hycon", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, data)
        editor.commit()
    }

    fun removePreferences(context: Context, key: String) {
        val pref = context.getSharedPreferences("hycon", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.remove(key)
        editor.commit()
    }
}