package ar.com.wolox.android.trainingFS.ui.training.details

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import ar.com.wolox.android.R
import ar.com.wolox.android.trainingFS.model.EventMessage
import ar.com.wolox.android.trainingFS.model.NewsItem
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import kotlinx.android.synthetic.main.fragment_details.*
import org.greenrobot.eventbus.EventBus
import org.ocpsoft.prettytime.PrettyTime
import java.util.Locale
import javax.inject.Inject

class DetailsFragment @Inject constructor() : WolmoFragment<DetailsPresenter>(), IDetailsView {

    private lateinit var newsDetail: NewsItem

    override fun layout(): Int = R.layout.fragment_details

    override fun handleArguments(arguments: Bundle?) = arguments != null && arguments.containsKey(KEY_NEWS_ITEM)

    override fun init() {
        val itemExtra = arguments!!.getSerializable(KEY_NEWS_ITEM)!!
        newsDetail = itemExtra as NewsItem
        presenter.onInit(newsDetail)
    }

    override fun showArgumentsError() {
        Toast.makeText(context, getString(R.string.error_invalid_arguments), Toast.LENGTH_LONG).show()
        activity?.finish()
    }

    override fun updateView(item: NewsItem) {
        newsDetail = item
        vTitle.text = newsDetail.title
        vDescription.text = newsDetail.text
        if (newsDetail.picture.isNotEmpty()) {
            val uri = Uri.parse(newsDetail.picture)
            vPicture.setImageURI(uri)
        }

        val prettyTime = PrettyTime(Locale.getDefault())
        vDate.text = prettyTime.format(newsDetail.getDate())

        vLikeBtn.setImageResource(if (newsDetail.getLike()) R.drawable.ic_like_on else R.drawable.ic_like_off)
    }

    override fun setListeners() {
        vLikeBtn.setOnClickListener {
            presenter.onLikeRequest(newsDetail)
        }
    }

    override fun changeLike(status: Boolean) {
        vLikeBtn.setImageResource(if (status) R.drawable.ic_like_on else R.drawable.ic_like_off)
    }

    override fun postChanges(item: NewsItem) {
        val eventMessage = EventMessage(item)
        EventBus.getDefault().post(eventMessage)
    }

    override fun enableLikeBtn() {
        vLikeBtn.isClickable = true
    }

    override fun disableLikeBtn() {
        vLikeBtn.isClickable = false
    }

    override fun isNetworkAvailable(): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    override fun showNetworkUnavailableError() {
        Toast.makeText(context, getString(R.string.error_network_unavailable), Toast.LENGTH_LONG).show()
    }

    override fun showServiceError() {
        Toast.makeText(context, getString(R.string.error_news_service), Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val KEY_NEWS_ITEM = "NEWS"
    }
}