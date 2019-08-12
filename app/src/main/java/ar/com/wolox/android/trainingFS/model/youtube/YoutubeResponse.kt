package ar.com.wolox.android.trainingFS.model.youtube

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class YoutubeResponse(
    @SerializedName("kind") val kind: String,
    @SerializedName("etag") val etag: String,
    @SerializedName("prevPageToken") val prevPageToken: String,
    @SerializedName("nextPageToken") val nextPageToken: String,
    @SerializedName("items") val items: List<YoutubeItem>
) : Serializable