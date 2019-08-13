package ar.com.wolox.android.trainingFS.ui.training.adapter

import ar.com.wolox.android.trainingFS.model.NewsItem

interface NewsPutServiceAdapterListener {

    fun onSuccess(newsItem: NewsItem)
    fun onEmptyData()
    fun onError()
}