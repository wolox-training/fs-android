package ar.com.wolox.android.training.model.youtube

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class YoutubeResponse(
    @SerializedName("kind") val kind: String,
    @SerializedName("etag") val etag: String,
    @SerializedName("nextPageToken") val nextPageToken: String,
    @SerializedName("regionCode") val regionCode: String,
    @SerializedName("items") val items: List<YoutubeItem>
) : Serializable