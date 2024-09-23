package com.example.wvtest.api

import com.example.wvtest.model.Content
import retrofit2.Call
import retrofit2.http.GET

interface ContentApi {

    @GET("/resource/data.json")
    fun getContent(): Call<Content>
}