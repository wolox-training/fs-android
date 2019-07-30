package ar.com.wolox.android.training.network

import ar.com.wolox.android.training.model.News
import retrofit2.http.GET

interface INewsService {

    @GET("/news")
    fun getNewsRequest(): List<News>
}