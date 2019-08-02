package ar.com.wolox.android.training.ui.training.details

import ar.com.wolox.android.training.model.NewsItem

interface IDetailsView {

    fun changeLike(status: Boolean)
    fun postChanges(item: NewsItem, position: Int)
}