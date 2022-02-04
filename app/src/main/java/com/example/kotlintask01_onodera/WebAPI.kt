package com.example.kotlintask01_onodera

import retrofit2.Call
import retrofit2.http.GET

interface WebAPI {
    @GET("/share/scc2018/countries.json")
    fun getCountries(): Call<List<CountryEntity>>
}