package ar.com.wolox.android.training.model.youtube

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class YoutubeThubnails(
    @SerializedName("default") val default: YoutubeThumbnailsItem,
    @SerializedName("medium") val medium: YoutubeThumbnailsItem,
    @SerializedName("high") val high: YoutubeThumbnailsItem
) : Serializable