package ar.com.wolox.android.training.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.Date

data class NewsItem(val title: String, val text: String) : Serializable {
    var picture: String = ""
    var userLike: Boolean = false
    var date: Date = Date()
    var likes: List<Int> = arrayListOf()

    @SerializedName("createdAt")
    var createdAt: String = ""
}