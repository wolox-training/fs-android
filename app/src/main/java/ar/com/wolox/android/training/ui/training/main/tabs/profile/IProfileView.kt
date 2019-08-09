package ar.com.wolox.android.training.ui.training.main.tabs.profile

import ar.com.wolox.android.training.model.youtube.YoutubeAdapterResponse
import ar.com.wolox.android.training.model.youtube.YoutubeListItem

interface IProfileView {

    fun isNetworkAvailable(): Boolean
    fun showNetworkUnavailableError()

    fun initProfileList(serviceData: YoutubeAdapterResponse)
    fun updateProfileList(serviceData: YoutubeAdapterResponse)

    fun reproduceVideo(url: String)

    fun showEmptyData()

    fun hideSoftKeyboard()

    fun notifyNetworkCnxError()

    fun goToDetails(item: YoutubeListItem)
}