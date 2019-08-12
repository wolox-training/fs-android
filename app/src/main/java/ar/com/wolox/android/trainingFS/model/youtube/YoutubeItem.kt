package ar.com.wolox.android.trainingFS.model.youtube

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class YoutubeItem(
    @SerializedName("kind") val kind: String,
    @SerializedName("etag") val etag: String,
    @SerializedName("id") val id: YoutubeItemId,
    @SerializedName("snippet") val snippet: YoutubeSnippet
) : Serializable