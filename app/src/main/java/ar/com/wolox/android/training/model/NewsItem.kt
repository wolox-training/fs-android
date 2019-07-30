package ar.com.wolox.android.training.model

import com.google.gson.annotations.SerializedName
import java.util.Date

class NewsItem(val title: String, val text: String) {
    var picture: String = ""
    var userLike: Boolean = false
    var date: Date = Date()
    var likes: List<Int> = arrayListOf()

    @SerializedName("createdAt")
    var createdAt: String = ""
}