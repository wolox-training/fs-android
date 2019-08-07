package ar.com.wolox.android.training.model.youtube

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class YoutubeListItem(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String
) : Serializable