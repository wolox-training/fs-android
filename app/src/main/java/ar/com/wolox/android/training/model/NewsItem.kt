package ar.com.wolox.android.training.model

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class NewsItem(
    val title: String,
    val text: String,
    var picture: String,
    var userLike: Boolean,
    val likes: List<Int>,
    @SerializedName("createdAt")
    var createdAt: String
) {
    fun updateLike(userId: Int) {
        if (this.likes.isNotEmpty() && this.likes.contains(userId)) {
            this.userLike = true
        }
    }

    fun getDate(): Date {
        return if (this.createdAt.isNotEmpty()) {
            SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).parse(this.createdAt)
        } else {
            Date()
        }
    }

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss.sss'Z'"
    }
}