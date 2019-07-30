package ar.com.wolox.android.training.model

class News {

    var userId: Int = -1
    var createdAt: String = ""
    var picture: String = ""
    var text: String = ""
    var likes: List<Int> = arrayListOf()
}