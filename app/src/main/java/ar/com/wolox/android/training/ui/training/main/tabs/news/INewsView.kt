package ar.com.wolox.android.training.ui.training.main.tabs.news

import ar.com.wolox.android.training.model.NewsItem

interface INewsView {

    fun isNetworkAvailable(): Boolean

    fun updateNews(newsItems: List<NewsItem>)
    fun showEmptyList()
    fun enableRefresh()
    fun disableRefresh()

    fun showServiceError()
    fun showEmptyDataError()

    fun showNetworkUnavailabeError()

    fun addNewToList(items: List<NewsItem>)
}