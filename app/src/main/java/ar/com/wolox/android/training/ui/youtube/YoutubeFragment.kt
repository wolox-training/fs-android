package ar.com.wolox.android.training.ui.youtube

import android.os.Bundle
import ar.com.wolox.android.R
import ar.com.wolox.android.training.model.youtube.YoutubeListItem
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import kotlinx.android.synthetic.main.fragment_youtube.*

class YoutubeFragment : WolmoFragment<BasePresenter<Any>>() {

    private lateinit var youtubeItem: YoutubeListItem

    override fun handleArguments(arguments: Bundle?) = arguments != null && arguments.containsKey(KEY_YOUTUBE)

    override fun layout(): Int = R.layout.fragment_youtube

    override fun init() {
        val itemExtra = arguments!!.getSerializable(KEY_YOUTUBE)!!
        youtubeItem = itemExtra as YoutubeListItem

        vChannel.text = youtubeItem.channelTitle
        vCreationDate.text = youtubeItem.creationDate
        vTitle.text = youtubeItem.title
        vDescription.text = youtubeItem.description
    }

    companion object {
        private const val KEY_YOUTUBE = "YOUTUBE"
    }
}