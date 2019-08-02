package ar.com.wolox.android.training.ui.training.main.tabs.news

import ar.com.wolox.android.training.model.NewsItem

interface NewsGetServiceAdapterListener {

    fun onSuccess(newsList: List<NewsItem>)
    fun onEmptyData()
    fun onError()
}