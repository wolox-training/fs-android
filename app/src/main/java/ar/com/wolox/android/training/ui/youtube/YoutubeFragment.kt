package ar.com.wolox.android.training.ui.youtube

import ar.com.wolox.android.R
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import ar.com.wolox.wolmo.core.presenter.BasePresenter

class YoutubeFragment : WolmoFragment<BasePresenter<Any>>() {

    override fun layout(): Int = R.layout.fragment_youtube

    override fun init() {
        println("DONE")
    }
}