package ar.com.wolox.android.trainingFS.model

import ar.com.wolox.android.trainingFS.utils.CredentialsSession
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

    @Transient lateinit var credentialsSession: CredentialsSession

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

    fun getLike(): Boolean {
        return this.likes.isNotEmpty() && this.likes.contains(credentialsSession.id)
    }

    fun modifyLike() {
        if (likes.contains(credentialsSession.id)) {
            likes.remove(credentialsSession.id)
        } else {
            likes.add(credentialsSession .id)
        }
    }

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss.sss'Z'"
    }
}