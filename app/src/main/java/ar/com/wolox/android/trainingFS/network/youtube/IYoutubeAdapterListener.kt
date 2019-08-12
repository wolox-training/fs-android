package ar.com.wolox.android.trainingFS.network.youtube

import ar.com.wolox.android.trainingFS.model.youtube.YoutubeAdapterResponse

interface IYoutubeAdapterListener {

    fun onFailure()
    fun onEmptyData()
    fun onSuccess(response: YoutubeAdapterResponse)
}