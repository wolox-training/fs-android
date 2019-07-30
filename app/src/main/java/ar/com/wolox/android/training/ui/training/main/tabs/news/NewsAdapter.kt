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
        private var mUserView: TextView? = null
        private var mNewsView: TextView? = null
        private var mUserIcon: SimpleDraweeView? = null
        private var mLikeBtn: ImageView? = null
        private var mDate: TextView? = null

        init {
            mUserView = itemView.vUsername
            mNewsView = itemView.vMessage
            mUserIcon = itemView.vUserIcon
            mLikeBtn = itemView.vLikeBtn
            mDate = itemView.vDate
        }

        fun bind(news: NewsItem, clickListener: (NewsItem) -> Unit) {
            mUserView?.text = news.user
            mNewsView?.text = news.message

            if (news.icon.isNotEmpty()) {
                val uri = Uri.parse(news.icon)
                mUserIcon?.setImageURI(uri)
            }

            val prettyTime = PrettyTime(Locale.getDefault())
            mDate?.text = prettyTime.format(news.date)

            mLikeBtn?.setImageResource(if (news.like) R.drawable.ic_like_on else R.drawable.ic_like_off)
            mLikeBtn?.setOnClickListener { clickListener(news) }
        }
    }
}