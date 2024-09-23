package com.example.wvtest.api.repository

import com.example.wvtest.api.ContentApi
import com.example.wvtest.api.ApiManager
import com.example.wvtest.model.Content
import com.example.wvtest.model.ContentShelfs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContentRepository {

    companion object {

        private var instance: ContentRepository? = null
        fun newInstance(): ContentRepository {
            if (instance == null) instance = ContentRepository()
            return instance!!
        }

    }


    fun getContent(callback:(res:ArrayList<ContentShelfs>?) -> Unit){
        ApiManager.getRetrofit().create(ContentApi::class.java).getContent().enqueue(object :Callback<Content>{
            override fun onResponse(call: Call<Content>?, response: Response<Content>?) {

            }

            override fun onFailure(call: Call<Content>?, t: Throwable?) {

            }

        })
    }
}


