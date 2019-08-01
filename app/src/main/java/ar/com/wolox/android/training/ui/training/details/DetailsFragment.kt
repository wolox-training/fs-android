package ar.com.wolox.android.training.ui.training.details

import android.net.Uri
import android.os.Bundle
import ar.com.wolox.android.R
import ar.com.wolox.android.training.model.NewsItem
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import com.facebook.drawee.backends.pipeline.Fresco
import kotlinx.android.synthetic.main.fragment_details.*
import org.ocpsoft.prettytime.PrettyTime
import java.util.Locale

class DetailsFragment : WolmoFragment<DetailsPresenter>(), IDetailsView {

    private lateinit var newsDetail: NewsItem
    private var position: Int = -1

    override fun layout(): Int = R.layout.fragment_details

    override fun handleArguments(arguments: Bundle?) = arguments != null

    override fun init() {
        val itemExtra = arguments?.getSerializable(KEY_NEWS_ITEM)
        newsDetail = itemExtra as NewsItem

        val posExtra = arguments?.getSerializable(KEY_NEWS_POSITION)
        position = posExtra as Int

        Fresco.initialize(context)

        vTitle.text = newsDetail.title
        vDescription.text = newsDetail.text
        if (newsDetail.picture.isNotEmpty()) {
            val uri = Uri.parse(newsDetail.picture)
            vPicture.setImageURI(uri)
        }

        val prettyTime = PrettyTime(Locale.getDefault())
        vDate.text = prettyTime.format(newsDetail.date)

        vLikeBtn.setImageResource(if (newsDetail.userLike) R.drawable.ic_like_on else R.drawable.ic_like_off)
    }

    override fun setListeners() {
        vLikeBtn.setOnClickListener {
            presenter.onLikeRequest(newsDetail, position)
        }
    }

    override fun changeLike(status: Boolean) {
        vLikeBtn.setImageResource(if (status) R.drawable.ic_like_on else R.drawable.ic_like_off)
    }

    companion object {
        private const val KEY_NEWS_ITEM = "NEWS"
        private const val KEY_NEWS_POSITION = "POSITION"
    }
}