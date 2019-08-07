package ar.com.wolox.android.training.ui.training.main.tabs.profile

import ar.com.wolox.android.training.model.youtube.YoutubeAdapterResponse
import ar.com.wolox.android.training.network.youtube.IYoutubeAdapterListener
import ar.com.wolox.android.training.network.youtube.YoutubeAdapter
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import javax.inject.Inject

class ProfilePresenter @Inject constructor(
    private val adapter: YoutubeAdapter
) : BasePresenter<IProfileView>() {

    fun onInit() {
    }

    fun onSearchRequest(query: String) {
        // view.reproduceVideo("2ZBtPf7FOoM")

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