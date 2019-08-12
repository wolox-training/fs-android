package ar.com.wolox.android.trainingFS.ui.training.main.tabs.profile

import ar.com.wolox.android.trainingFS.model.youtube.YoutubeAdapterResponse
import ar.com.wolox.android.trainingFS.model.youtube.YoutubeListItem
import ar.com.wolox.android.trainingFS.network.youtube.IYoutubeAdapterListener
import ar.com.wolox.android.trainingFS.network.youtube.YoutubeAdapter
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import javax.inject.Inject

class ProfilePresenter @Inject constructor(
    private val adapter: YoutubeAdapter
) : BasePresenter<IProfileView>() {

    var downloadingData: Boolean = false
    lateinit var lastQuery: String

    fun onInit() {
        view.showEmptyData()
    }

    fun onSearchRequest(query: String) {

        view.hideSoftKeyboard()
        if (query.isEmpty()) {
            view.showEmptyData()
        } else {
            if (!view.isNetworkAvailable()) {
                view.showNetworkUnavailableError()
            } else {
                lastQuery = query
                adapter.getSongs(query, "", object : IYoutubeAdapterListener {
                    override fun onFailure() {
                        view.showEmptyData()
                    }

                    override fun onEmptyData() {
                        view.showEmptyData()
                    }

                    override fun onSuccess(response: YoutubeAdapterResponse) {
                        // view.reproduceVideo(response.listItem[0].id)
                        view.initProfileList(response)
                    }
                })
            }
        }
    }

    fun onItemClickRequest(item: YoutubeListItem) {
        // Ej: URL := "https://www.youtube.com/watch?v=2G5rfPISIwo" => id := "2G5rfPISIwo"
        view.reproduceVideo(item.id)
    }

    fun onEndOfList(nextPageToken: String) {
        if (!downloadingData) {
            if (!view.isNetworkAvailable()) {
                view.showNetworkUnavailableError()
            } else {
                downloadingData = true
                adapter.getSongs(lastQuery, nextPageToken, object : IYoutubeAdapterListener {
                    override fun onFailure() {
                        downloadingData = false
                        view.notifyNetworkCnxError()
                    }

                    override fun onEmptyData() {
                        downloadingData = false
                        view.notifyNetworkCnxError()
                    }

                    override fun onSuccess(response: YoutubeAdapterResponse) {
                        downloadingData = false
                        view.updateProfileList(response)
                    }
                })
            }
        }
    }
}