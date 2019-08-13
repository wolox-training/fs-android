package ar.com.wolox.android.trainingFS.ui.training.adapter

import ar.com.wolox.android.trainingFS.model.NewsItem

interface NewsGetServiceAdapterListener {

    fun onSuccess(newsList: List<NewsItem>)
    fun onEmptyData()
    fun onError()
}