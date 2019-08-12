package ar.com.wolox.android.trainingFS.ui.training.details

import ar.com.wolox.android.trainingFS.model.NewsItem
import ar.com.wolox.android.trainingFS.ui.training.adapter.NewsPutServiceAdapterListener
import ar.com.wolox.android.trainingFS.ui.training.adapter.NewsServiceAdapter
import ar.com.wolox.android.trainingFS.utils.CredentialsSession
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import javax.inject.Inject

class DetailsPresenter @Inject constructor(
    private val serviceAdapter: NewsServiceAdapter,
    private val credentialsSession: CredentialsSession
) : BasePresenter<IDetailsView>() {

    fun onInit(item: NewsItem) {
        if (item.text.isEmpty() || item.text.isEmpty() || item.picture.isEmpty()) {
            view.showArgumentsError()
        }
        item.credentialsSession = credentialsSession
        view.updateView(item)
    }

    fun onLikeRequest(item: NewsItem) {
        item.modifyLike()
        view.changeLike(item.getLike())
        view.disableLikeBtn()

        if (!view.isNetworkAvailable()) {
            view.showNetworkUnavailableError()
        } else {
            serviceAdapter.modifyNews(news = item, listener = object : NewsPutServiceAdapterListener {
                override fun onSuccess(newsItem: NewsItem) {
                    newsItem.credentialsSession = credentialsSession
                    view.enableLikeBtn()
                    view.postChanges(item)
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
}