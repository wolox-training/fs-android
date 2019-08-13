package ar.com.wolox.android.trainingFS.network.youtube

import android.content.Context
import android.util.Log
import ar.com.wolox.android.trainingFS.model.youtube.YoutubeResponse
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
        val body: HashMap<String, String> = hashMapOf(KEY_PART to VALUE_PART, KEY_QUERY to query,
                KEY_API to VALUE_API, KEY_TYPE to VALUE_TYPE, KEY_MAX to VALUE_MAX, KEY_TOKEN to pageToken)

        val call = api.searchSongs(body)
        call.enqueue(object : Callback<YoutubeResponse> {
            override fun onResponse(call: Call<YoutubeResponse>, response: Response<YoutubeResponse>) {

                var result = response.body()
                if (!response.isSuccessful) {
                    // TODO: Simulation Data Response from json assets (KeyExpired or Request Limit Exceeded)
                    Log.e("YTSimulation", "ResponseError: " + response.errorBody()?.string())
                    result = getSampleFromAssets()
                }

                if (result != null) {
                    listener.onSuccess(result)
                } else {
                    listener.onEmptyData()
                }
            }

            override fun onFailure(call: Call<YoutubeResponse>, t: Throwable) {
                listener.onFailure()
            }
        })
    }

    private fun getSampleFromAssets(): YoutubeResponse? {
        val inputStream = context.assets.open("youtube_sample_extended.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val json = String(buffer)

        val gson = GsonBuilder().create()
        return gson.fromJson(json, YoutubeResponse::class.java)
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
