package ar.com.wolox.android.training.ui.training.main.tabs.news

import android.graphics.Color
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.com.wolox.android.R
import ar.com.wolox.android.training.model.NewsItem
import ar.com.wolox.android.training.utils.onClickListener
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import com.facebook.drawee.backends.pipeline.Fresco
import kotlinx.android.synthetic.main.fragment_news.*
import javax.inject.Inject

class NewsFragment @Inject constructor() : WolmoFragment<NewsPresenter>(), INewsView {

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun layout(): Int = R.layout.fragment_news

    override fun init() {
        Fresco.initialize(context)

        vRefreshListLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)
        vRefreshListLayout.setOnRefreshListener {
            presenter.refreshRecyclerView()
        }

        vRefreshEmptyList.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)
        vRefreshEmptyList.setOnRefreshListener {
            presenter.refreshRecyclerView()
        }

        viewManager = LinearLayoutManager(context)
        presenter.refreshRecyclerView()
    }

    override fun updateRecyclerView(newsItems: List<NewsItem>) {
        vRefreshListLayout.visibility = View.VISIBLE
        vRefreshEmptyList.visibility = View.GONE

        viewAdapter = NewsAdapter(newsItems) { partItem: NewsItem -> partItemClicked(partItem) }
        vRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }!!
        viewAdapter.notifyDataSetChanged()
    }

    override fun showEmptyList() {
        vRefreshListLayout.visibility = View.GONE
        vRefreshEmptyList.visibility = View.VISIBLE
    }

    override fun enableRefresh() {
        vRefreshListLayout.isRefreshing = true
        vRefreshEmptyList.isRefreshing = true
    }

    override fun disableRefresh() {
        vRefreshListLayout.isRefreshing = false
        vRefreshEmptyList.isRefreshing = false
    }

    override fun setListeners() {
        vFab.onClickListener {
            // TODO: Call the presenter and delete the code below
            Toast.makeText(context, getString(R.string.news_fab), Toast.LENGTH_SHORT).show()
        }
    }

    private fun partItemClicked(item: NewsItem) {
        item.like = !item.like
        viewAdapter.notifyDataSetChanged()
    }
}