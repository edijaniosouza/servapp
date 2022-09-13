package br.com.edijanio.servapp.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    private val client by lazy {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build();
    }

    private val retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl("https://api.hgbrasil.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val chamadosApiService: ChamadosService by lazy {
        retrofit.create(ChamadosService::class.java)
    }
}