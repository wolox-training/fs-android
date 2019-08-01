package ar.com.wolox.android.training.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class NewsItem(
    val title: String,
    val text: String,
    var picture: String,
    var userLike: Boolean,
    var date: Date,
    val likes: List<Int>,
    @SerializedName("createdAt")
    var createdAt: String
)