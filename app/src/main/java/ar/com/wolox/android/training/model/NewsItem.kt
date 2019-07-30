package ar.com.wolox.android.training.model

import java.util.Date

class NewsItem(val title: String, val text: String) {
    var picture: String = ""
    var userLike: Boolean = false
    var date: Date = Date()
    var userUd: Int = -1
    var likes: List<Int> = arrayListOf()
    var createdAt: String = ""
}