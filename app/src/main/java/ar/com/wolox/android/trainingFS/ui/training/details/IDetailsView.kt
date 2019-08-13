package ar.com.wolox.android.trainingFS.ui.training.details

import ar.com.wolox.android.trainingFS.model.NewsItem

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