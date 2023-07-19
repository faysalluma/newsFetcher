package com.example.testmobile.data.network

import com.example.testmobile.data.Constants
import com.example.testmobile.data.network.bodies.results.Article
import com.example.testmobile.data.network.bodies.results.ResponseMessage
import retrofit2.Call
import retrofit2.http.*

interface SampleApiInterface {
    @GET("/v2/top-headlines")
    fun getAllArticles(@Query("country") country: String, @Query("apiKey") apiKey: String): Call<ResponseMessage>
}