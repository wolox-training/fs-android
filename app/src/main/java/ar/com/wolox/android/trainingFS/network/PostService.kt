package ar.com.wolox.android.trainingFS.network

import ar.com.wolox.android.trainingFS.model.Post

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PostService {

    @GET("/posts/{id}")
    fun getPostById(@Path("id") id: Int): Call<Post>
}
