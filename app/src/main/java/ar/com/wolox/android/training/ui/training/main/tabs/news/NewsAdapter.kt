package ar.com.wolox.android.training.ui.training.main.tabs.news

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.com.wolox.android.R
import ar.com.wolox.android.training.model.NewsItem
import kotlinx.android.synthetic.main.news_item.view.*
import org.ocpsoft.prettytime.PrettyTime
import java.util.Locale

class NewsAdapter(private val dataSet: List<NewsItem>, private val clickListener: (NewsItem) -> Unit) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news: NewsItem = dataSet[position]
        holder.bind(news, clickListener)
    }

    override fun getItemCount(): Int = dataSet.size

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(news: NewsItem, clickListener: (NewsItem) -> Unit) {
            itemView.vUsername.text = news.user
            itemView.vMessage.text = news.message

            if (news.icon.isNotEmpty()) {
                val uri = Uri.parse(news.icon)
                itemView.vUserIcon.setImageURI(uri)
            }

            val prettyTime = PrettyTime(Locale.getDefault())
            itemView.vDate.text = prettyTime.format(news.date)

            itemView.vLikeBtn.setImageResource(if (news.like) R.drawable.ic_like_on else R.drawable.ic_like_off)
            itemView.vLikeBtn.setOnClickListener { clickListener(news) }
        }
    }
}