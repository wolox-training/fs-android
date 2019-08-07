package ar.com.wolox.android.training.network.youtube

import ar.com.wolox.android.training.model.youtube.YoutubeAdapterResponse
import ar.com.wolox.android.training.model.youtube.YoutubeListItem
import ar.com.wolox.android.training.model.youtube.YoutubeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class YoutubeAdapter @Inject constructor() {

    fun getSongs(query: String, nextPageToken: String, listener: IYoutubeAdapterListener) {
        val retrofit: Retrofit = Retrofit.Builder()
                                    .baseUrl(BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build()

        val api = retrofit.create(YoutubeService::class.java)
        val body: HashMap<String, String> = hashMapOf()
        body[KEY_PART] = VALUE_PART

        if (query.isNotEmpty()) {
            body[KEY_QUERY] = query
        }

        body[KEY_API] = VALUE_API
        body[KEY_TYPE] = VALUE_TYPE
        body[KEY_MAX] = VALUE_MAX

        if (nextPageToken.isNotEmpty()) {
            body[KEY_TOKEN] = nextPageToken
        }

        val call = api.searchSongs(body)
        call.enqueue(object : Callback<YoutubeResponse> {
            override fun onResponse(call: Call<YoutubeResponse>, response: Response<YoutubeResponse>) {
                if (response.body() != null) {
                    val result: YoutubeResponse = response.body()!!

                    val videoList = mutableListOf<YoutubeListItem>()
                    result.items.forEach {
                        val item = YoutubeListItem(it.id.videoId, it.snippet.title, it.snippet.description)
                        videoList.add(item)
                    }

                    val adapterResponse = YoutubeAdapterResponse(result.nextPageToken, videoList)

                    listener.onSuccess(adapterResponse)
                } else {
                    listener.onEmptyData()
                }
            }

            override fun onFailure(call: Call<YoutubeResponse>, t: Throwable) {
                listener.onFailure()
            }
        })
    }

    companion object {
        private const val BASE_URL = "https://www.googleapis.com/youtube/v3/"

        private const val KEY_PART = "part"
        private const val KEY_QUERY = "q"
        private const val KEY_API = "key"
        private const val KEY_TYPE = "type"
        private const val KEY_MAX = "maxResults"
        private const val KEY_TOKEN = "pageToken"

        private const val VALUE_PART = "snippet"
        private const val VALUE_API = "AIzaSyC4VaaeF5ig9nIJByd13hsnOOzUjPvO6WM"
        private const val VALUE_TYPE = "video"
        private const val VALUE_MAX = "10"
    }
}
