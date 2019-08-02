package ar.com.wolox.android.training.model

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class NewsItem(
    val title: String,
    val text: String,
    var picture: String,
    val likes: MutableList<Int>,
    @SerializedName("createdAt")
    var createdAt: String
) {
    fun getDate(): Date {
        return if (this.createdAt.isNotEmpty()) {
            SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).parse(this.createdAt)
        } else {
            Date()
        }
    }

    fun getLike(userId: Int): Boolean {
        return this.likes.isNotEmpty() && this.likes.contains(userId)
    }

    fun setLike(userId: Int, status: Boolean) {
        if (status) {
            likes.add(userId)
        } else {
            likes.remove(userId)
        }
    }

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss.sss'Z'"
    }
}