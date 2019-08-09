package ar.com.wolox.android.training.network.youtube

import android.content.Context
import ar.com.wolox.android.training.model.youtube.YoutubeAdapterResponse
import ar.com.wolox.android.training.model.youtube.YoutubeListItem
import ar.com.wolox.android.training.model.youtube.YoutubeResponse
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class YoutubeAdapter @Inject constructor(val context: Context) {

    fun getSongs(query: String, pageToken: String, listener: IYoutubeAdapterListener) {
        val retrofit: Retrofit = Retrofit.Builder()
                                    .baseUrl(BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build()

        val api = retrofit.create(YoutubeService::class.java)
        val body: HashMap<String, String> = hashMapOf()
        body[KEY_PART] = VALUE_PART
        body[KEY_QUERY] = query
        body[KEY_API] = VALUE_API
        body[KEY_TYPE] = VALUE_TYPE
        body[KEY_MAX] = VALUE_MAX

        if (pageToken.isNotEmpty()) {
            body[KEY_TOKEN] = pageToken
        }

        val call = api.searchSongs(body)
        call.enqueue(object : Callback<YoutubeResponse> {
            override fun onResponse(call: Call<YoutubeResponse>, response: Response<YoutubeResponse>) {
                if (response.body() != null) {
                    val result: YoutubeResponse = response.body()!!

                    val videoList = mutableListOf<YoutubeListItem>()
                    result.items.forEach {
                        val item = YoutubeListItem(it.id.videoId, it.snippet.title, it.snippet.description,
                                it.snippet.thumbnails.default.url, it.snippet.thumbnails.medium.url,
                                it.snippet.thumbnails.high.url)
                        videoList.add(item)
                    }

                    val adapterResponse = YoutubeAdapterResponse(result.prevPageToken, result.nextPageToken, videoList)

                    listener.onSuccess(adapterResponse)
                } else {
                    //listener.onEmptyData()

                    // TODO: Simulation, QueryRequest from json file (ExpiredKey or limit exceeded)
                    val sampleResponse = getSampleFromAssets()
                    if (sampleResponse == null) {
                        listener.onEmptyData()
                    } else {
                        listener.onSuccess(sampleResponse)
                    }
                }
            }

            override fun onFailure(call: Call<YoutubeResponse>, t: Throwable) {
                listener.onFailure()
            }
        })
    }

    private fun getSampleFromAssets(): YoutubeAdapterResponse? {
        val inputStream = context.assets.open("youtube_sample_extended.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val json = String(buffer)

        val gson = GsonBuilder().create()
        val result = gson.fromJson(json, YoutubeResponse::class.java)
        val videoList = mutableListOf<YoutubeListItem>()
        result.items.forEach {
            val item = YoutubeListItem(it.id.videoId, it.snippet.title, it.snippet.description,
                    it.snippet.thumbnails.default.url, it.snippet.thumbnails.medium.url,
                    it.snippet.thumbnails.high.url)
            videoList.add(item)
        }
        return YoutubeAdapterResponse(result.nextPageToken, result.nextPageToken, videoList)
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
        private const val VALUE_API = "AIzaSyB_7puJZkQvPawdT61QIygRCajnXYsXsJo"
        private const val VALUE_TYPE = "video"
        private const val VALUE_MAX = "10"
    }
}
