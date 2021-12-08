package com.example.currencyconverterapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {
    private var retrofit : Retrofit? = null
    fun getClient():Retrofit?{
        retrofit =Retrofit.Builder()
            .baseUrl(" https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }

}