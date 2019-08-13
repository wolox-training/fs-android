package ar.com.wolox.android.trainingFS.model.youtube

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class YoutubeItem(
    @SerializedName("kind") val kind: String,
    @SerializedName("etag") val etag: String,
    @SerializedName("id") private val id: YoutubeItemId,
    @SerializedName("snippet") private val snippet: YoutubeSnippet
) : Serializable {

    val title: String
        get() = snippet.title

    val description: String
        get() = snippet.description

    val videoId: String
        get() = id.videoId

    val publishedAt: String
        get() = snippet.publishedAt

    val channelID: String
        get() = snippet.channelId

    val channelTitle: String
        get() = snippet.channelTitle

    val defaultUrlImg: String
        get() = snippet.thumbnails.default.url

    val mediumUrlImg: String
        get() = snippet.thumbnails.medium.url

    val highUrlImg: String
        get() = snippet.thumbnails.high.url
}