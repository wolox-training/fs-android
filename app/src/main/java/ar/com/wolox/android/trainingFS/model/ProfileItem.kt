package ar.com.wolox.android.trainingFS.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ProfileItem(
    @SerializedName("nextPageToken") val nextPage: String,
    @SerializedName("prevPageToken") val prevPage: String
) : Serializable
