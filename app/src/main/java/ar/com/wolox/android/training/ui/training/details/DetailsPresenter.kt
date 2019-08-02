package ar.com.wolox.android.training.ui.training.details

import ar.com.wolox.android.training.model.NewsItem
import ar.com.wolox.android.training.ui.training.adapter.NewsPutServiceAdapterListener
import ar.com.wolox.android.training.ui.training.adapter.NewsServiceAdapter
import ar.com.wolox.android.training.utils.CredentialsSession
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import javax.inject.Inject

class DetailsPresenter @Inject constructor(
    private val credentialsSession: CredentialsSession,
    private val mServiceAdapter: NewsServiceAdapter
) : BasePresenter<IDetailsView>() {

    fun onLikeRequest(item: NewsItem, position: Int) {
        item.modifyLike(credentialsSession.id)
        view.changeLike(item.getLike(credentialsSession.id))
        view.disableLikeBtn()

        if (!view.isNetworkAvailable()) {
            view.showNetworkUnavailableError()
        } else {
            mServiceAdapter.modifyNews(position + POSITION_IN_SERVICE, item, object : NewsPutServiceAdapterListener {
                override fun onSuccess(newsItem: NewsItem) {
                    view.enableLikeBtn()
                    view.postChanges(item, position)
                }

                override fun onEmptyData() {
                    view.enableLikeBtn()
                    view.showServiceError()
                }

                override fun onError() {
                    view.enableLikeBtn()
                    view.showServiceError()
                }
            })
        }
    }

    companion object {
        private const val POSITION_IN_SERVICE = 1
    }
}