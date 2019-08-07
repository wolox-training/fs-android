package ar.com.wolox.android.training.ui.training.main.tabs.profile

import ar.com.wolox.android.training.model.youtube.YoutubeAdapterResponse
import ar.com.wolox.android.training.model.youtube.YoutubeListItem
import ar.com.wolox.android.training.network.youtube.IYoutubeAdapterListener
import ar.com.wolox.android.training.network.youtube.YoutubeAdapter
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import javax.inject.Inject

class ProfilePresenter @Inject constructor(
    private val adapter: YoutubeAdapter
) : BasePresenter<IProfileView>() {

    fun onInit() {
        view.showEmptyData()
    }

    fun onSearchRequest(query: String) {

        view.hideSoftKeyboard()
        if (query.isEmpty()) {
            view.showEmptyData()
        } else {
            adapter.getSongs(query, object : IYoutubeAdapterListener {
                override fun onFailure() {
                    view.showEmptyData()
                }

                override fun onEmptyData() {
                    view.showEmptyData()
                }

                override fun onSuccess(response: YoutubeAdapterResponse) {
                    // view.reproduceVideo(response.listItem[0].id)
                    view.updateProfileList(response)
                }
            })
        }
    }

    fun onItemClickRequest(item: YoutubeListItem) {
        // Ej: URL := "https://www.youtube.com/watch?v=2G5rfPISIwo" => id := "2G5rfPISIwo"
        view.reproduceVideo(item.id)
    }
}