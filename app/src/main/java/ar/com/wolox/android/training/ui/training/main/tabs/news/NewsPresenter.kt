package ar.com.wolox.android.training.ui.training.main.tabs.news

import ar.com.wolox.android.training.model.NewsItem
import ar.com.wolox.android.training.network.INewsService
import ar.com.wolox.android.training.utils.CredentialsSession
import ar.com.wolox.android.training.utils.networkCallback
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import ar.com.wolox.wolmo.networking.retrofit.RetrofitServices
import javax.inject.Inject

class NewsPresenter @Inject constructor(
    private val mRetrofitServices: RetrofitServices,
    private val userCredential: CredentialsSession
) : BasePresenter<INewsView>() {

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
        if (userId > -1) {
            for (news in newsList) {
                if (news.likes.isNotEmpty() && news.likes.contains(userId)) {
                    news.userLike = true
                }
            }
        }

        view.updateRecyclerView(newsList)
    }
}