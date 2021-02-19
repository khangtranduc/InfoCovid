package com.example.infocovid.services

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsServiceBuilder {
    private val URL = "https://vaccovid-coronavirus-vaccine-and-treatment-tracker.p.rapidapi.com/api/news/"
    private val okHttp = OkHttpClient.Builder().addInterceptor{chain ->
        val request: Request = chain.request().newBuilder()
             .addHeader("x-rapidapi-key", "39b64fae87msh989732b2e41495cp1ce1edjsn4d625f39575e")
             .addHeader("x-rapidapi-host", "vaccovid-coronavirus-vaccine-and-treatment-tracker.p.rapidapi.com")
             .build()

        return@addInterceptor chain.proceed(request)
    }

    private val builder = Retrofit.Builder().baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp.build())

    private val retrofit = builder.build()

    fun <T> builderService (serviceType : Class<T>): T{
        return retrofit.create(serviceType)
    }
}