package com.heisy.myapplication

import android.app.Application
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder





// Расширил класс Application, чтобы производить настройку retrofit 1 раз,
// При запуске приложения

class CordsApp: Application(){

    // Переменная для реализации интерфейса CordsApi
    lateinit var cordsApi: CordsApi

    override fun onCreate() {
        super.onCreate()
        configureRetrofit()
    }

    /**
     * Функция настройки retrofit
     * Настроенный retrofit записывает в переменную cordsApi класса MyApp
     */
    private fun configureRetrofit(){


        // Здесь проходит настройка retrofit

        val httpLoggingInterceptor =  HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit= Retrofit.Builder()
            .baseUrl("https://waadsu.com/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        cordsApi = retrofit.create(CordsApi::class.java)
    }
}
