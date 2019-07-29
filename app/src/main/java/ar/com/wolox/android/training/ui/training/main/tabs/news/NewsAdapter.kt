package ar.com.wolox.android.training.ui.training.main.tabs.news

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ar.com.wolox.android.R
import ar.com.wolox.android.training.model.NewsItem
import com.facebook.drawee.view.SimpleDraweeView
import org.ocpsoft.prettytime.PrettyTime
import java.util.Locale

class NewsAdapter(private val dataSet: List<NewsItem>, private val clickListener: (NewsItem) -> Unit) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.news_item, parent, false)) {
        private var mUserView: TextView? = null
        private var mNewsView: TextView? = null
        private var mUserIcon: SimpleDraweeView? = null
        private var mLikeBtn: ImageView? = null
        private var mDate: TextView? = null

        init {
            mUserView = itemView.findViewById(R.id.vUsername)
            mNewsView = itemView.findViewById(R.id.vMessage)
            mUserIcon = itemView.findViewById(R.id.vUserIcon)
            mLikeBtn = itemView.findViewById(R.id.vLikeBtn)
            mDate = itemView.findViewById(R.id.vDate)
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

            if (news.like) {
                mLikeBtn?.setImageResource(R.drawable.ic_like_on)
            } else {
                mLikeBtn?.setImageResource(R.drawable.ic_like_off)
            }

            mLikeBtn?.setOnClickListener { clickListener(news) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news: NewsItem = dataSet[position]
        holder.bind(news, clickListener)
    }

    override fun getItemCount(): Int = dataSet.size
}