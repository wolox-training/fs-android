package ar.com.wolox.android.training.model.youtube

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class YoutubeAdapterResponse(
    @SerializedName("nextPageToken") var nextPageToken: String,
    @SerializedName("listItem") val listItem: MutableList<YoutubeListItem>
) : Serializable