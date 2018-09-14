package com.example.baehyeonbin.neverending_nightmare.utils

import android.content.Context
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

object RetrofitUtil {

    var retrofit = Retrofit.Builder()
            .baseUrl("http://13.124.86.54:23002/api/v1g1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val mlRetrofit : Retrofit = Retrofit.Builder()
            .baseUrl("http://52.79.106.90:5050")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val MULTIPART_FORM_DATA = "multipart/form-data"

    fun getLoginRetrofit(context: Context): Retrofit {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val token : String = SharedPreferenceUtil.getPreference(context, "token")!!
            val request = original.newBuilder()
                    .header("x-access-token", token)
                    .method(original.method(), original.body())
                    .build()
            chain.proceed(request)
        }

        val client = httpClient.build()
        return Retrofit.Builder()
                .baseUrl("http://13.124.86.54:23002/api/v1g1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
    }

    fun createRequestBody(file: File, name: String): MultipartBody.Part {
        val mFile = RequestBody.create(MediaType.parse("images/*"), file)
        return MultipartBody.Part.createFormData(name, file.name, mFile)
    }

    fun createRequestBody(value: String): RequestBody {
        return RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), value)
    }
}
