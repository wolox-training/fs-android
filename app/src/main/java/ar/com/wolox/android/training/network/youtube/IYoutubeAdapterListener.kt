package ar.com.wolox.android.training.network.youtube

import ar.com.wolox.android.training.model.youtube.YoutubeAdapterResponse

interface IYoutubeAdapterListener {

    fun onFailure()
    fun onEmptyData()
    fun onSuccess(response: YoutubeAdapterResponse)
}