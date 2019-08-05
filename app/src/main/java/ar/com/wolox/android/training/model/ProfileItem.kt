package ar.com.wolox.android.training.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ProfileItem(
    @SerializedName("nextPageToken") val nextPage: String,
    @SerializedName("prevPageToken") val prevPage: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("items") val items: List<YoutubeItem>
) : Serializable
