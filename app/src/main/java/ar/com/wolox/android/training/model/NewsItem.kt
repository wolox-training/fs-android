package ar.com.wolox.android.training.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class NewsItem(
    val title: String,
    val text: String,
    var picture: String,
    var userLike: Boolean,
    var date: Date,
    val likes: List<Int>,
    @SerializedName("createdAt")
    var createdAt: String
) : Serializable {
    fun updateDate() {
        if (this.createdAt.isNotEmpty()) {
            val formatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            this.date = formatter.parse(this.createdAt)
        }
    }

    fun updateLike(userId: Int) {
        if (this.likes.isNotEmpty() && this.likes.contains(userId)) {
            this.userLike = true
        }
    }

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss.sss'Z'"
    }
}