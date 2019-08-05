package ar.com.wolox.android.training.model

import com.google.gson.annotations.SerializedName

class YoutubeItem(
    @SerializedName("channelId") val channelId: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("url") val url: String,
    @SerializedName("channelTitle") val channelTitle: String
)