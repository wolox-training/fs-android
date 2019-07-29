package ar.com.wolox.android.training.ui.training.main.tabs.news

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ar.com.wolox.android.R
import ar.com.wolox.android.training.model.NewsItem

class NewsAdapter(private val dataSet: List<NewsItem>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.news_item, parent, false)) {
        private var mUserView: TextView? = null
        private var mNewsView: TextView? = null

        init {
            mUserView = itemView.findViewById(R.id.list_user)
            mNewsView = itemView.findViewById(R.id.list_msg)
        }

        fun bind(news: NewsItem) {
            mUserView?.text = news.user
            mNewsView?.text = news.message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val movie: NewsItem = dataSet[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = dataSet.size
}