package ar.com.wolox.android.training.ui.training.main.tabs.news

import android.annotation.SuppressLint
import ar.com.wolox.android.training.model.NewsItem
import ar.com.wolox.android.training.network.INewsService
import ar.com.wolox.android.training.utils.CredentialsSession
import ar.com.wolox.android.training.utils.networkCallback
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import ar.com.wolox.wolmo.networking.retrofit.RetrofitServices
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

private const val DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss.sss'Z'"
private const val ICON_DEFAULT = "http://pngimg.com/uploads/android_logo/android_logo_PNG3.png"

class NewsPresenter @Inject constructor(
    private val mRetrofitServices: RetrofitServices,
    private val userCredential: CredentialsSession
) : BasePresenter<INewsView>() {

    @SuppressLint("SimpleDateFormat")
    val formatter: SimpleDateFormat = SimpleDateFormat(DATE_FORMAT)

    override fun onViewAttached() {
        refreshRecyclerView()
    }

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

        view.updateNews(newsList)
    }

    fun addDummyElements(lastIndex: Int) {
        // TODO: (Simulation) Dummy method to generate infinity items for recyclerView
        val newsList = mutableListOf<NewsItem>()

        for (count in 1..5) {
            val newsItem = NewsItem("Title $lastIndex($count)", "Body of the dummy message number $lastIndex($count)")
            newsItem.date = Date()
            newsItem.userLike = (lastIndex % 3 == 0)
            newsItem.picture = ICON_DEFAULT

            newsList.add(newsItem)
        }

        view.addNewToList(newsList)
    }
}