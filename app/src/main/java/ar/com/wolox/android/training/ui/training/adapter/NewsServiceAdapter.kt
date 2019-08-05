package ar.com.wolox.android.training.ui.training.adapter

import ar.com.wolox.android.training.model.NewsItem
import ar.com.wolox.android.training.network.NewsService
import ar.com.wolox.android.training.utils.networkCallback
import ar.com.wolox.wolmo.networking.retrofit.RetrofitServices
import javax.inject.Inject

class NewsServiceAdapter @Inject constructor(private val retrofitServices: RetrofitServices) {

    fun getNews(listener: NewsGetServiceAdapterListener) {
        retrofitServices.getService(NewsService::class.java).getNewsRequest().enqueue(
                networkCallback {
                    onResponseSuccessful { response ->
                        response?.let { listener.onSuccess(it) } ?: listener.onEmptyData()
                    }

                    onResponseFailed { _, _ ->
                        listener.onError()
                    }

                    onCallFailure {
                        listener.onError()
                    }
                }
        )
    }

    fun modifyNews(news: NewsItem, listener: NewsPutServiceAdapterListener) {
        retrofitServices.getService(NewsService::class.java).putNewsRequest(news.id, news).enqueue(
                networkCallback {
                    onResponseSuccessful { response ->
                        response?.let { listener.onSuccess(response) } ?: listener.onEmptyData()
                    }

                    onResponseFailed { _, _ ->
                        listener.onError()
                    }

                    onCallFailure {
                        listener.onError()
                    }
                }
        )
    }
}