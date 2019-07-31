package ar.com.wolox.android.training.ui.training.main.tabs.news

import ar.com.wolox.android.training.network.INewsService
import ar.com.wolox.android.training.utils.networkCallback
import ar.com.wolox.wolmo.networking.retrofit.RetrofitServices
import javax.inject.Inject

class NewsServiceAdapter @Inject constructor(private val mRetrofitServices: RetrofitServices) {

    fun getNews(listener: NewsServiceAdapterListener) {
        mRetrofitServices.getService(INewsService::class.java).getNewsRequest().enqueue(
                networkCallback {
                    onResponseSuccessful { response ->
                        if (response != null) {
                            listener.onSuccess(response)
                        } else {
                            listener.onEmptyData()
                        }
                    }

                    onResponseFailed { _, _ ->
                        run {
                            listener.onError()
                        }
                    }

                    onCallFailure {
                        listener.onError()
                    }
                }
        )
    }
}