package com.example.infocovid.services

import com.example.infocovid.model.Country
import com.example.infocovid.model.New
import com.example.infocovid.model.Vaccine
import retrofit2.Call
import retrofit2.http.GET

interface GetService {
    @GET("countries")
    fun getCountries() : Call<List<Country>>

    @GET("vaccine")
    fun getVaccines() : Call<Vaccine>

    @GET("get-vaccine-news/0")
    fun getVaccNews() : Call<New>

    @GET("get-health-news/1")
    fun getHealthNews() : Call<New>

    @GET("get-coronavirus-news/0")
    fun getCovNews() : Call<New>
}