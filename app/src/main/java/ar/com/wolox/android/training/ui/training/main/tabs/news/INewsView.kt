package ar.com.wolox.android.training.ui.training.main.tabs.news

import ar.com.wolox.android.training.model.NewsItem

interface INewsView {

    fun updateNews(newsItems: List<NewsItem>)
    fun showEmptyList()
    fun enableRefresh()
    fun disableRefresh()
}