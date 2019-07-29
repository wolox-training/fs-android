package ar.com.wolox.android.training.ui.training.main.tabs.news

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.com.wolox.android.R
import ar.com.wolox.android.training.model.NewsItem
import ar.com.wolox.android.training.utils.onClickListener
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import com.facebook.drawee.backends.pipeline.Fresco
import kotlinx.android.synthetic.main.fragment_news.*
import javax.inject.Inject

private const val ICON = "http://pngimg.com/uploads/android_logo/android_logo_PNG3.png"

class NewsFragment @Inject constructor() : WolmoFragment<BasePresenter<Any>>() {

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun layout(): Int = R.layout.fragment_news

    override fun init() {
        val mNewsExample = newsListGenerator()

        Fresco.initialize(context)

        viewManager = LinearLayoutManager(context)
        viewAdapter = NewsAdapter(mNewsExample) { partItem: NewsItem -> partItemClicked(partItem) }
        vRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }!!
    }

    override fun setListeners() {
        vFab.onClickListener {
            Toast.makeText(context, getString(R.string.news_fab), Toast.LENGTH_SHORT).show()
        }
    }

    private fun partItemClicked(item: NewsItem) {
        item.like = !item.like
        viewAdapter.notifyDataSetChanged()
    }

    private fun newsListGenerator(): List<NewsItem> {
        // TODO: Sample List Init(), delete after dataset is created
        val newsList = mutableListOf<NewsItem>()
        var count = 0

        while (count < 15) {
            val username = "User$count"
            val message = "Message " + (count + 1)
            val item = NewsItem(username, message, ICON)
            newsList.add(item)

            count++
        }
        return newsList
    }
}