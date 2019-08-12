package ar.com.wolox.android.trainingFS.model.youtube

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class YoutubeListItem(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("defaultImg") val defaultImg: String,
    @SerializedName("mediumImg") val mediumImg: String,
    @SerializedName("highImg") val highImg: String,
    @SerializedName("channelTitle") val channelTitle: String,
    @SerializedName("publishedAt") val publishedAt: String
) : Serializable