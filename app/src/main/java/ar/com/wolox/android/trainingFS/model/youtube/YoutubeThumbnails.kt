package ar.com.wolox.android.trainingFS.model.youtube

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class YoutubeThumbnails(
    @SerializedName("default") val default: YoutubeThumbnailsItem,
    @SerializedName("medium") val medium: YoutubeThumbnailsItem,
    @SerializedName("high") val high: YoutubeThumbnailsItem
) : Serializable