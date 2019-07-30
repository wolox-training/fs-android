package ar.com.wolox.android.training.ui.training.main.tabs.news

import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
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

    private lateinit var viewAdapter: NewsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var newsItemList: MutableList<NewsItem>

    override fun layout(): Int = R.layout.fragment_news

    @RequiresApi(Build.VERSION_CODES.M)
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

        vRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView
                        .layoutManager as LinearLayoutManager?
                if (linearLayoutManager!!.itemCount <= linearLayoutManager.findLastVisibleItemPosition() + 2) {
                    presenter.addDummyElements(linearLayoutManager.findLastVisibleItemPosition())
                }
            }
        })

        viewManager = LinearLayoutManager(context)
        presenter.refreshRecyclerView()
    }

    override fun updateRecyclerView(newsItems: List<NewsItem>) {
        vRefreshListLayout.visibility = View.VISIBLE
        vRefreshEmptyList.visibility = View.GONE

        newsItemList = mutableListOf()
        newsItemList.addAll(newsItems)

        viewAdapter = NewsAdapter(newsItemList) { partItem: NewsItem -> partItemClicked(partItem) }
        vRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
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

    override fun showServiceError() {
        Toast.makeText(context, getString(R.string.error_news_service), Toast.LENGTH_LONG).show()
    }

    override fun showEmptyDataError() {
        Toast.makeText(context, getString(R.string.error_news_empty_data), Toast.LENGTH_LONG).show()
    }

    private fun partItemClicked(item: NewsItem) {
        item.userLike = !item.userLike
        viewAdapter.notifyDataSetChanged()
    }

    override fun addNewToList(items: List<NewsItem>) {
        val auxList = mutableListOf<NewsItem>()
        auxList.addAll(newsItemList)
        auxList.addAll(newsItemList.size, items)
        newsItemList = auxList

        viewAdapter.addData(items)
    }
}