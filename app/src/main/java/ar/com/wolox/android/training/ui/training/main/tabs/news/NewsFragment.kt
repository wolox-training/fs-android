package ar.com.wolox.android.training.ui.training.main.tabs.news

import ar.com.wolox.android.R
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import javax.inject.Inject

class NewsFragment @Inject constructor() : WolmoFragment<BasePresenter<Any>>() {

    override fun layout(): Int = R.layout.fragment_news

    override fun init() {
    }
}