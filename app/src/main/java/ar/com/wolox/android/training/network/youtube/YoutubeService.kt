package ar.com.wolox.android.training.network.youtube

import ar.com.wolox.android.training.model.youtube.YoutubeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface YoutubeService {

    @GET("search")
    fun searchSongs(@QueryMap options: Map<String, String>): Call<YoutubeResponse>
}