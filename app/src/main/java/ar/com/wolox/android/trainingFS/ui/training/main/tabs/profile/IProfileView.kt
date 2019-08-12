package ar.com.wolox.android.trainingFS.ui.training.main.tabs.profile

import ar.com.wolox.android.trainingFS.model.youtube.YoutubeAdapterResponse

interface IProfileView {

    fun isNetworkAvailable(): Boolean
    fun showNetworkUnavailableError()

    fun initProfileList(serviceData: YoutubeAdapterResponse)
    fun updateProfileList(serviceData: YoutubeAdapterResponse)

    fun reproduceVideo(url: String)

    fun showEmptyData()

    fun hideSoftKeyboard()

    fun notifyNetworkCnxError()
}