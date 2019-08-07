package ar.com.wolox.android.training.ui.training.main.tabs.profile

import ar.com.wolox.android.training.model.ProfileItem
import ar.com.wolox.android.training.model.youtube.YoutubeResponse
import ar.com.wolox.android.training.network.youtube.IYoutubeAdapterListener
import ar.com.wolox.android.training.network.youtube.YoutubeAdapter
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import javax.inject.Inject

class ProfilePresenter @Inject constructor(
    private val adapter: YoutubeAdapter
) : BasePresenter<IProfileView>() {

    fun onInit() {
        val sampleList = mutableListOf<ProfileItem>()
        val sampleItem = ProfileItem(
                "NextSample",
                "PrevSample"
        )
        sampleList.add(sampleItem)

        view.updateProfileList(sampleList)
    }

    fun onSearchRequest(query: String) {
        // view.reproduceVideo("2ZBtPf7FOoM")

        adapter.getSongs("queen", object : IYoutubeAdapterListener {
            override fun onFailure() {
                println("FAILURE")
            }

            override fun onEmptyData() {
                println("EMPTY DATA")
            }

            override fun onSuccess(response: YoutubeResponse) {
                println("SUCCESS")
                view.reproduceVideo(response.items[0].id.videoId)
            }
        })
    }
}