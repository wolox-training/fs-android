package ar.com.wolox.android.training.ui.training.details

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import ar.com.wolox.android.R
import ar.com.wolox.android.training.model.EventMessage
import ar.com.wolox.android.training.model.NewsItem
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import com.facebook.drawee.backends.pipeline.Fresco
import kotlinx.android.synthetic.main.fragment_details.*
import org.greenrobot.eventbus.EventBus
import org.ocpsoft.prettytime.PrettyTime
import java.util.Locale

class DetailsFragment : WolmoFragment<DetailsPresenter>(), IDetailsView {

    private lateinit var newsDetail: NewsItem
    private var position: Int = -1
    private var userId: Int = -1

    override fun layout(): Int = R.layout.fragment_details

    override fun handleArguments(arguments: Bundle?) = arguments != null

    override fun init() {
        val itemExtra = arguments?.getSerializable(KEY_NEWS_ITEM)
        newsDetail = itemExtra as NewsItem

        val posExtra = arguments?.getSerializable(KEY_NEWS_POSITION)
        position = posExtra as Int

        val idExtra = arguments?.getSerializable(KEY_USER_ID)
        userId = idExtra as Int

        Fresco.initialize(context)

        vTitle.text = newsDetail.title
        vDescription.text = newsDetail.text
        if (newsDetail.picture.isNotEmpty()) {
            val uri = Uri.parse(newsDetail.picture)
            vPicture.setImageURI(uri)
        }

        val prettyTime = PrettyTime(Locale.getDefault())
        vDate.text = prettyTime.format(newsDetail.getDate())

        vLikeBtn.setImageResource(if (newsDetail.getLike(userId)) R.drawable.ic_like_on else R.drawable.ic_like_off)
    }

    override fun setListeners() {
        vLikeBtn.setOnClickListener {
            presenter.onLikeRequest(newsDetail, position)
        }
    }

    override fun changeLike(status: Boolean) {
        vLikeBtn.setImageResource(if (status) R.drawable.ic_like_on else R.drawable.ic_like_off)
    }

    override fun postChanges(item: NewsItem, position: Int) {
        val eventMessage = EventMessage(item, position)
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
        private const val KEY_NEWS_POSITION = "POSITION"
        private const val KEY_USER_ID = "USERID"
    }
}