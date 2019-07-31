package ar.com.wolox.android.training.ui.training.main.tabs.news

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ar.com.wolox.android.R
import ar.com.wolox.android.training.model.NewsItem
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.news_item.view.*
import org.ocpsoft.prettytime.PrettyTime
import java.util.Locale

class NewsListAdapter(private val dataSet: MutableList<NewsItem>, private val clickListener: (NewsItem) -> Unit) : RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news: NewsItem = dataSet[position]
        holder.bind(news, clickListener)
    }

    override fun getItemCount(): Int = dataSet.size

    fun addData(listOfSigns: List<NewsItem>) {
        this.dataSet.addAll(listOfSigns)
        notifyDataSetChanged()
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var vUsername: TextView? = null
        private var vMessage: TextView? = null
        private var vIcon: SimpleDraweeView? = null
        private var vLike: ImageView? = null
        private var vDate: TextView? = null

        init {
            vUsername = itemView.vUsername
            vMessage = itemView.vMessage
            vIcon = itemView.vUserIcon
            vLike = itemView.vLikeBtn
            vDate = itemView.vDate
        }

        fun bind(news: NewsItem, clickListener: (NewsItem) -> Unit) {
            vUsername?.text = news.title
            vMessage?.text = news.text

            if (news.picture.isNotEmpty()) {
                val uri = Uri.parse(news.picture)
                vIcon?.setImageURI(uri)
            }

            val prettyTime = PrettyTime(Locale.getDefault())
            vDate?.text = prettyTime.format(news.date)

            vLike?.setImageResource(if (news.userLike) R.drawable.ic_like_on else R.drawable.ic_like_off)
            vLike?.setOnClickListener { clickListener(news) }
        }
    }
}