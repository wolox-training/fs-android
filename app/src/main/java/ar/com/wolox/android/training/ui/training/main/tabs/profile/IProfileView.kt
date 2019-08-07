package ar.com.wolox.android.training.ui.training.main.tabs.profile

import ar.com.wolox.android.training.model.youtube.YoutubeAdapterResponse

interface IProfileView {

    fun isNetworkAvailable(): Boolean

    fun updateProfileList(serviceData: YoutubeAdapterResponse)

    fun reproduceVideo(url: String)

    fun showEmptyData()
}