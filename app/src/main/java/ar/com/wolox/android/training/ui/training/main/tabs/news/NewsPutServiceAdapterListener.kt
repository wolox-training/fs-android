package ar.com.wolox.android.training.ui.training.main.tabs.news

import ar.com.wolox.android.training.model.NewsItem

interface NewsPutServiceAdapterListener {

    fun onSuccess(newsItem: NewsItem)
    fun onEmptyData()
    fun onError()
}