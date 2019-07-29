package ar.com.wolox.android.training.ui.training.main.tabs.news

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.com.wolox.android.R
import ar.com.wolox.android.training.model.NewsItem
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import javax.inject.Inject

class NewsFragment @Inject constructor() : WolmoFragment<BasePresenter<Any>>() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun layout(): Int = R.layout.fragment_news

    override fun init() {
        val mNewsExample = newsListGenerator()

        viewManager = LinearLayoutManager(context)
        viewAdapter = NewsAdapter(mNewsExample)

        recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)?.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }!!
    }

    private fun newsListGenerator(): List<NewsItem> {
        //TODO: Sample List Init(), delete after dataset is created
        val newsList = mutableListOf<NewsItem>()
        var count = 0

        while (count < 15) {
            val username = "User$count"
            val message = "Message " + (count + 1)
            val item = NewsItem(username, message)
            newsList.add(item)

            count++
        }
        return newsList
    }
}