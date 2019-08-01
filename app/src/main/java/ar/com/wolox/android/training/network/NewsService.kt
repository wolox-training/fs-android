package ar.com.wolox.android.training.network

import ar.com.wolox.android.training.model.NewsItem
import retrofit2.Call
import retrofit2.http.GET

interface NewsService {

    @GET("/news")
    fun getNewsRequest(): Call<List<NewsItem>>
}