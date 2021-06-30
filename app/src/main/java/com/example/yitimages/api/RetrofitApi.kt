package com.example.yitimages.api

import com.example.yitimages.utils.Utils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApi {

    val mRetrofitApi: ImagesApi by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Utils.BASE_URL).build().create(ImagesApi::class.java)
    }
}