package com.example.currencyconverter

import android.icu.util.Currency
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

public interface APIInterface {
   @GET("eur.json")
 fun getCurrency(): Call<Currency>?

}