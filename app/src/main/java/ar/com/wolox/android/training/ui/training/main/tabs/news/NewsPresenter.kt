package ar.com.wolox.android.training.ui.training.main.tabs.news

import ar.com.wolox.android.training.model.EventMessage
import ar.com.wolox.android.training.model.NewsItem
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import javax.inject.Inject

class NewsPresenter @Inject constructor(
    private val mServiceAdapter: NewsServiceAdapter
) : BasePresenter<INewsView>() {

    override fun onViewAttached() {
        onUpdateNewsRequest()
    }

    fun onUpdateNewsRequest() {
        view.enableRefresh()

        if (!view.isNetworkAvailable()) {
            view.disableRefresh()
            view.showNetworkUnavailableError()
        } else {
            mServiceAdapter.getNews(object : NewsGetServiceAdapterListener {
                override fun onSuccess(newsList: List<NewsItem>) {
                    view.disableRefresh()
                    view.updateNews(fillDataList(newsList))
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
    }

    fun onEventMessageRequest(eventMessage: EventMessage) {
        view.replaceItemAtIndex(eventMessage.item, eventMessage.position)
    }

    fun onLikeRequest(position: Int, item: NewsItem) {
        if (view.isUploadingData()) {
            view.showUploadingError()
        } else {
            view.disableLikeBtn()
            if (!view.isNetworkAvailable()) {
                view.showNetworkUnavailableError()
            } else {
                view.modifyLike(item)
                mServiceAdapter.modifyNews(position = item.id, body = item, listener = object : NewsPutServiceAdapterListener {
                    override fun onSuccess(newsItem: NewsItem) {
                        view.enableLikeBtn()
                        view.replaceItemAtIndex(newsItem, position)
                    }

                    override fun onEmptyData() {
                        view.enableLikeBtn()
                        view.modifyLike(item)
                        view.showEmptyDataError()
                    }

                    override fun onError() {
                        view.enableLikeBtn()
                        view.modifyLike(item)
                        view.showServiceError()
                    }
                })
            }
        }
    }

    private fun fillDataList(dataList: List<NewsItem>): List<NewsItem> {
        // TODO: Fill list with dummy objects from service
        return if (dataList.size < 5) {
            val auxList = mutableListOf<NewsItem>()
            auxList.addAll(dataList)
            auxList.addAll(generateSimulationItems(dataList.size))
            auxList
        } else {
            dataList
        }
    }

    private fun generateSimulationItems(index: Int): MutableList<NewsItem> {
        // TODO: (Simulation) Dummy method to generate 5 dummy objects
        val newsList = mutableListOf<NewsItem>()

        for (count in 1..5) {
            val newsItem = NewsItem(index + count,
                    2,
                    "2019-08-01T10:00:00.000Z",
                    "Title $index($count)",
                    ICON_DEFAULT,
                    "Body of the dummy message number $index($count)",
                    mutableListOf())

            newsList.add(newsItem)
        }
        return newsList
    }

    fun onEndOfList(lastIndex: Int) {
        // TODO: (Simulation) Dummy method to add items to recyclerView
        view.addNewToList(generateSimulationItems(lastIndex))
    }

    companion object {
        private const val ICON_DEFAULT = "http://pngimg.com/uploads/android_logo/android_logo_PNG3.png"
    }
}
