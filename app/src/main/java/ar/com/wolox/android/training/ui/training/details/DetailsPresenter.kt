package ar.com.wolox.android.training.ui.training.details

import ar.com.wolox.android.training.model.NewsItem
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import javax.inject.Inject

class DetailsPresenter @Inject constructor() : BasePresenter<IDetailsView>() {

    fun onLikeRequest(item: NewsItem, position: Int) {
        item.userLike = !item.userLike
        view.changeLike(item.userLike)
    }
}