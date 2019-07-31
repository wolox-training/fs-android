package ar.com.wolox.android.training.ui.training.details

import android.os.Bundle
import ar.com.wolox.android.R
import ar.com.wolox.android.training.model.NewsItem
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : WolmoFragment<BasePresenter<Any>>() {

    override fun layout(): Int = R.layout.fragment_details

    override fun handleArguments(arguments: Bundle?) = arguments != null && arguments.containsKey("NEW")

    override fun init() {
        val item = arguments?.getSerializable("NEW")
        val news = item as NewsItem
        vTextSample.text = news.text
    }
}