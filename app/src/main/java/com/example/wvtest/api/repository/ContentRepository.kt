package com.example.wvtest.api.repository

import android.util.Log
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


    fun getContent(callback:(response:ArrayList<ContentShelfs>?) -> Unit){
        ApiManager.getRetrofit().create(ContentApi::class.java).getContent().enqueue(object :Callback<Content>{
            override fun onResponse(call: Call<Content>?, response: Response<Content>?) {
                //log response
//                Log.d("ContentRepository",response?.body()?.responseObject?.contentShelfs?.size.toString() )
                callback.invoke(response?.body()?.responseObject?.contentShelfs)
            }

            override fun onFailure(call: Call<Content>?, t: Throwable?) {
                callback.invoke(null)
            }

        })
    }
}



