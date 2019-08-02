package ar.com.wolox.android.training.ui.training.main.tabs.news

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.com.wolox.android.R
import ar.com.wolox.android.training.model.NewsItem
import ar.com.wolox.android.training.ui.training.details.DetailsActivity
import ar.com.wolox.android.training.utils.CredentialsSession
import ar.com.wolox.android.training.utils.onClickListener
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import com.facebook.drawee.backends.pipeline.Fresco
import kotlinx.android.synthetic.main.fragment_news.*
import javax.inject.Inject

class NewsFragment @Inject constructor(private val credentialsSession: CredentialsSession) : WolmoFragment<NewsPresenter>(), INewsView {

    private lateinit var viewAdapter: NewsListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var newsItemList: MutableList<NewsItem>

    override fun layout(): Int = R.layout.fragment_news

    override fun init() {
        Fresco.initialize(context)

        vRefreshListLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)
        vRefreshEmptyList.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)

        viewManager = LinearLayoutManager(context)
    }

    override fun isNetworkAvailable(): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    override fun updateNews(newsItems: List<NewsItem>) {
        vRefreshListLayout.visibility = View.VISIBLE
        vRefreshEmptyList.visibility = View.GONE

        newsItemList = mutableListOf()
        newsItemList.addAll(newsItems)

        viewAdapter = NewsListAdapter(newsItemList, { item -> likeBtnClicked(item) }, { item, position -> detailsClicked(item, position) }, credentialsSession)

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

        vRefreshListLayout.setOnRefreshListener {
            presenter.onUpdateNewsRequest()
        }

        vRefreshEmptyList.setOnRefreshListener {
            presenter.onUpdateNewsRequest()
        }

        vRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView
                        .layoutManager as LinearLayoutManager?
                if (linearLayoutManager!!.itemCount <= linearLayoutManager.findLastVisibleItemPosition() + PADDING_TO_REFRESH) {
                    presenter.onEndOfList(linearLayoutManager.findLastVisibleItemPosition())
                }
            }
        })
    }

    override fun showServiceError() {
        Toast.makeText(context, getString(R.string.error_news_service), Toast.LENGTH_LONG).show()
    }

    override fun showEmptyDataError() {
        Toast.makeText(context, getString(R.string.error_news_empty_data), Toast.LENGTH_LONG).show()
    }

    override fun showNetworkUnavailabeError() {
        Toast.makeText(context, getString(R.string.error_network_unavailable), Toast.LENGTH_LONG).show()
    }

    private fun likeBtnClicked(item: NewsItem) {
        item.setLike(credentialsSession.id, !item.getLike(credentialsSession.id))
        viewAdapter.notifyDataSetChanged()
    }

    override fun addNewToList(items: List<NewsItem>) {
        viewAdapter.addData(items)
    }

    private fun detailsClicked(item: NewsItem, position: Int) {
        val intent = Intent(activity, DetailsActivity::class.java).apply {
            this.putExtra(KEY_NEWS_ITEM, item)
            this.putExtra(KEY_NEWS_POSITION, position)
            this.putExtra(KEY_USER_ID, credentialsSession.id)
        }
        startActivity(intent)
    }

    companion object {
        private const val PADDING_TO_REFRESH = 2
        private const val KEY_NEWS_ITEM = "NEWS"
        private const val KEY_NEWS_POSITION = "POSITION"
        private const val KEY_USER_ID = "USERID"
    }
}