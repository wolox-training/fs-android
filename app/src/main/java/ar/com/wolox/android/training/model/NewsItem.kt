package ar.com.wolox.android.training.model

import java.util.Date

data class NewsItem(val user: String, val message: String, val icon: String) {
    var like: Boolean = false
    val date: Date = Date()
}
