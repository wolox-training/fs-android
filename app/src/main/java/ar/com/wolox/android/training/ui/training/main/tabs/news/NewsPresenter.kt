package ar.com.wolox.android.training.ui.training.main.tabs.news

import android.os.Handler
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import javax.inject.Inject

class NewsPresenter @Inject constructor() : BasePresenter<INewsView>() {

    fun refreshRecyclerView() {

        //TODO: Refresh simulation, delete after backend implementation is over
        Handler().postDelayed({
            view.updateRecyclerView()
        }, 5000L)
    }
}