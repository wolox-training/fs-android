package ar.com.wolox.android.trainingFS.model.youtube

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class YoutubeItemId(
    @SerializedName("kind") val kind: String,
    @SerializedName("videoId") val videoId: String
) : Serializable