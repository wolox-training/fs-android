package ar.com.wolox.android.training.ui.training.main.tabs.news

import android.annotation.SuppressLint
import ar.com.wolox.android.training.model.NewsItem
import ar.com.wolox.android.training.utils.CredentialsSession
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

private const val DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss.sss'Z'"
private const val ICON_DEFAULT = "http://pngimg.com/uploads/android_logo/android_logo_PNG3.png"

class NewsPresenter @Inject constructor(
    private val mServiceAdapter: NewsServiceAdapter,
    private val userCredential: CredentialsSession
) : BasePresenter<INewsView>() {

    @SuppressLint("SimpleDateFormat")
    val formatter: SimpleDateFormat = SimpleDateFormat(DATE_FORMAT)

    override fun onViewAttached() {
        onUpdateNewsRequest()
    }

    fun onUpdateNewsRequest() {
        view.enableRefresh()

        mServiceAdapter.getNews(object : NewsServiceAdapterListener {
            override fun onSuccess(newsList: List<NewsItem>) {
                view.disableRefresh()
                parseLikesData(newsList)
            }

            override fun onEmptyData() {
                view.disableRefresh()
                view.showEmptyList()
                view.showEmptyDataError()
            }

            override fun onError() {
                view.disableRefresh()
                view.showEmptyList()
                view.showServiceError()
            }
        })
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
            val newsItem = NewsItem("Title $lastIndex($count)",
                    "Body of the dummy message number $lastIndex($count)",
                    ICON_DEFAULT,
                    false,
                    Date(),
                    listOf(),
                    "")

            newsList.add(newsItem)
        }

        view.addNewToList(newsList)
    }
}
