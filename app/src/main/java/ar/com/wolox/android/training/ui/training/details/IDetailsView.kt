package ar.com.wolox.android.training.ui.training.details

import ar.com.wolox.android.training.model.NewsItem

interface IDetailsView {

    fun showArgumentsError()
    fun updateView(item: NewsItem)

    fun changeLike(status: Boolean)
    fun postChanges(item: NewsItem)

    fun enableLikeBtn()
    fun disableLikeBtn()

    fun isNetworkAvailable(): Boolean

    fun showNetworkUnavailableError()
    fun showServiceError()
}