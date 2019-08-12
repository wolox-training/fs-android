package ar.com.wolox.android.trainingFS.ui.training.main.tabs.news

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.com.wolox.android.R
import ar.com.wolox.android.trainingFS.model.NewsItem
import kotlinx.android.synthetic.main.news_item.view.vDate
import kotlinx.android.synthetic.main.news_item.view.vLikeBtn
import kotlinx.android.synthetic.main.news_item.view.vMainContainer
import kotlinx.android.synthetic.main.news_item.view.vMessage
import kotlinx.android.synthetic.main.news_item.view.vUserIcon
import kotlinx.android.synthetic.main.news_item.view.vUsername
import org.ocpsoft.prettytime.PrettyTime
import java.util.Locale

class NewsListAdapter(
    private val dataSet: MutableList<NewsItem>,
    private val likeClickListener: (NewsItem) -> Unit,
    private val detailsClickListener: (NewsItem) -> Unit
) : RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news: NewsItem = dataSet[position]
        holder.bind(news, likeClickListener, detailsClickListener)
    }

    override fun getItemCount(): Int = dataSet.size

    fun addData(listOfSigns: List<NewsItem>) {
        this.dataSet.addAll(listOfSigns)
        notifyDataSetChanged()
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            news: NewsItem,
            likeClickListener: (NewsItem) -> Unit,
            detailsClickListener: (NewsItem) -> Unit
        ) {
            itemView.vUsername.text = news.title
            itemView.vMessage.text = news.text

            if (news.picture.isNotEmpty()) {
                val uri = Uri.parse(news.picture)
                itemView.vUserIcon.setImageURI(uri)
            }

            val prettyTime = PrettyTime(Locale.getDefault())

            itemView.vDate.text = prettyTime.format(news.getDate())

            itemView.vLikeBtn.setImageResource(if (news.getLike()) R.drawable.ic_like_on else R.drawable.ic_like_off)
            itemView.vLikeBtn.setOnClickListener { likeClickListener(news) }

            itemView.vMainContainer.setOnClickListener { detailsClickListener(news) }
        }
    }
}