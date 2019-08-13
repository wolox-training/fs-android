package ar.com.wolox.android.trainingFS.ui.training.main.tabs.profile

import ar.com.wolox.android.trainingFS.model.youtube.YoutubeResponse

interface IProfileView {

    fun isNetworkAvailable(): Boolean
    fun showNetworkUnavailableError()

    fun initProfileList(serviceData: YoutubeResponse)
    fun updateProfileList(serviceData: YoutubeResponse)

    fun reproduceVideo(url: String)

    fun showEmptyData()

    fun hideSoftKeyboard()

    fun notifyNetworkCnxError()
}