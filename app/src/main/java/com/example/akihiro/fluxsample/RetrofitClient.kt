package com.example.akihiro.fluxsample

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    private val baseURL: String by lazy {
        MyApplication.getApplication.getString(R.string.base_url)
    }

    private val gsonConverterFactory: GsonConverterFactory by lazy {
        GsonConverterFactory.create()
    }

    private val rxJava2CallAdapterFactory: RxJava2CallAdapterFactory by lazy {
        RxJava2CallAdapterFactory.create()
    }

    private val httpLoggingInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    val api: API by lazy {
        Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .client(okHttpClient)
            .build()
            .create(API::class.java)
    }
}