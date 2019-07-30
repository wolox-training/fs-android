package ar.com.wolox.android.training.ui.training.main.tabs.news

import android.os.Handler
import ar.com.wolox.android.training.model.NewsItem
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import javax.inject.Inject

private const val ICON = "http://pngimg.com/uploads/android_logo/android_logo_PNG3.png"

class NewsPresenter @Inject constructor() : BasePresenter<INewsView>() {

    // TODO (Simulation)
    var emptyList: Boolean = true
    var newsList: List<NewsItem> = mutableListOf()

    fun refreshRecyclerView() {
        view.enableRefresh()

        // TODO: Refresh simulation, delete after backend implementation is over
        if (emptyList) {
            emptyList = !emptyList
            view.disableRefresh()
            view.showEmptyList()
        } else {
            Handler().postDelayed({
                view.disableRefresh()
                if (newsList.isEmpty()) {
                    newsList = newsListGenerator()
                }
                view.updateRecyclerView(newsList)
            }, 5000L)
        }
    }

    private fun newsListGenerator(): List<NewsItem> {
        // TODO: Sample List Init(), delete after dataset method is created
        val newsList = mutableListOf<NewsItem>()
        var count = 0

        while (count < 15) {
            val username = "User$count"
            val message = "Message " + (count + 1)
            val item = NewsItem(username, message, ICON)
            newsList.add(item)

            count++
        }
        return newsList
    }
}