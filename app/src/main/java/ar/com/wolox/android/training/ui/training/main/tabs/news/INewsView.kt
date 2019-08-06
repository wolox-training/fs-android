package ar.com.wolox.android.training.ui.training.main.tabs.news

import ar.com.wolox.android.training.model.NewsItem

interface INewsView {

    fun isNetworkAvailable(): Boolean

    fun updateNews(newsItems: List<NewsItem>)
    fun showEmptyList()
    fun enableRefresh()
    fun disableRefresh()

    fun showUploadingError()

    fun showServiceError()
    fun showEmptyDataError()

    fun showNetworkUnavailableError()

    fun addNewToList(items: List<NewsItem>)

    fun replaceNews(item: NewsItem)

    fun refreshView()
}