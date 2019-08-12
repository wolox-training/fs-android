package ar.com.wolox.android.trainingFS.network

import ar.com.wolox.android.trainingFS.model.NewsItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface NewsService {

    @GET("/news")
    fun getNewsRequest(): Call<List<NewsItem>>

    @PUT("/news/{id}")
    fun putNewsRequest(@Path("id") id: Int, @Body body: NewsItem): Call<NewsItem>
}