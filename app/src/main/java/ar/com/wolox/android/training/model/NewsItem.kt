package ar.com.wolox.android.training.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class NewsItem(
    @SerializedName("id") val id: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("createdAt") var createdAt: String,
    @SerializedName("title") val title: String,
    @SerializedName("picture") var picture: String,
    @SerializedName("text") val text: String,
    @SerializedName("likes") val likes: MutableList<Int>

) : Serializable {

    fun getDate(): Date {
        return if (this.createdAt.isNotEmpty()) {
            try {
                SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).parse(this.createdAt)
            } catch (e: Exception) {
                Date()
            }
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