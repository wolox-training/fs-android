package ar.com.wolox.android.training.ui.training.details

import ar.com.wolox.android.training.model.NewsItem
import ar.com.wolox.android.training.utils.CredentialsSession
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import javax.inject.Inject

class DetailsPresenter @Inject constructor(private val credentialsSession: CredentialsSession) : BasePresenter<IDetailsView>() {

    fun onLikeRequest(item: NewsItem, position: Int) {
        item.setLike(credentialsSession.id, !item.getLike(credentialsSession.id))
        view.changeLike(item.getLike(credentialsSession.id))
    }
}