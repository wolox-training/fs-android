package ar.com.wolox.android.trainingFS.network.youtube

import ar.com.wolox.android.trainingFS.model.youtube.YoutubeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface YoutubeService {

    @GET("search")
    fun searchSongs(@QueryMap options: Map<String, String>): Call<YoutubeResponse>
}