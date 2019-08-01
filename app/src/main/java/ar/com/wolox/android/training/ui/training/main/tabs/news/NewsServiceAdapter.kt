package ar.com.wolox.android.training.ui.training.main.tabs.news

import ar.com.wolox.android.training.network.NewsService
import ar.com.wolox.android.training.utils.networkCallback
import ar.com.wolox.wolmo.networking.retrofit.RetrofitServices
import javax.inject.Inject

class NewsServiceAdapter @Inject constructor(private val retrofitServices: RetrofitServices) {

    fun getNews(listener: NewsServiceAdapterListener) {
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
}