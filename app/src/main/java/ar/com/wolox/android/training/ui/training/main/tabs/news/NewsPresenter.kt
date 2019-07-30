package ar.com.wolox.android.training.ui.training.main.tabs.news

import android.annotation.SuppressLint
import ar.com.wolox.android.training.model.NewsItem
import ar.com.wolox.android.training.network.INewsService
import ar.com.wolox.android.training.utils.CredentialsSession
import ar.com.wolox.android.training.utils.networkCallback
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import ar.com.wolox.wolmo.networking.retrofit.RetrofitServices
import java.text.SimpleDateFormat
import javax.inject.Inject

private const val DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss.sss'Z'"

class NewsPresenter @Inject constructor(
    private val mRetrofitServices: RetrofitServices,
    private val userCredential: CredentialsSession
) : BasePresenter<INewsView>() {

    @SuppressLint("SimpleDateFormat")
    val formatter: SimpleDateFormat = SimpleDateFormat(DATE_FORMAT)

    fun refreshRecyclerView() {
        view.enableRefresh()

        mRetrofitServices.getService(INewsService::class.java).getNewsRequest().enqueue(
                networkCallback {
                    onResponseSuccessful { response ->
                        runIfViewAttached { view ->
                            view.disableRefresh()
                            if (response != null) {
                                parseLikesData(response)
                            } else {
                                view.showEmptyList()
                                view.showEmptyDataError()
                            }
                        }
                    }

                    onResponseFailed { _, _ -> runIfViewAttached(Runnable {
                        view.disableRefresh()
                        view.showEmptyList()
                        view.showServiceError()
                    }) }

                    onCallFailure { runIfViewAttached(Runnable {
                        view.disableRefresh()
                        view.showEmptyList()
                        view.showServiceError()
                    }) }
                }
        )
    }

    private fun parseLikesData(newsList: List<NewsItem>) {
        val userId = userCredential.id

        for (news in newsList) {
            news.date = formatter.parse(news.createdAt)

            if (news.likes.isNotEmpty() && news.likes.contains(userId)) {
                news.userLike = true
            }
        }

        view.updateRecyclerView(newsList)
    }
}