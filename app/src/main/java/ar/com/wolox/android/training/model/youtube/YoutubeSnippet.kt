package ar.com.wolox.android.training.model.youtube

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class YoutubeSnippet(
    @SerializedName("publishedAt") val publishedAt: String,
    @SerializedName("channelId") val channelId: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("channelTitle") val channelTitle: String
) : Serializable