package ar.com.wolox.android.training.ui.training.main.tabs.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.com.wolox.android.R
import ar.com.wolox.android.training.model.youtube.YoutubeAdapterResponse
import ar.com.wolox.android.training.model.youtube.YoutubeListItem
import ar.com.wolox.android.training.ui.youtube.YoutubeActivity
import ar.com.wolox.android.training.utils.onClickListener
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import com.facebook.drawee.backends.pipeline.Fresco
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

class ProfileFragment @Inject constructor() : WolmoFragment<ProfilePresenter>(), IProfileView {

    private lateinit var viewAdapter: ProfileListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var profileList: MutableList<YoutubeListItem>
    private lateinit var nextPageToken: String

    override fun layout(): Int = R.layout.fragment_profile

    override fun init() {
        Fresco.initialize(context)

        viewManager = LinearLayoutManager(context)
        presenter.onInit()
    }

    override fun isNetworkAvailable(): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    override fun showNetworkUnavailableError() {
        Toast.makeText(context, getString(R.string.error_network_unavailable), Toast.LENGTH_LONG).show()
    }

    override fun setListeners() {
        vSearch.onClickListener {
            presenter.onSearchRequest(vSearchQuery.text.toString())
        }

        vRecyclerViewProfiles.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val linearLayoutManager = recyclerView
                        .layoutManager as LinearLayoutManager?
                if (linearLayoutManager!!.itemCount <= linearLayoutManager.findLastVisibleItemPosition() +
                        PADDING_TO_REFRESH) {
                    presenter.onEndOfList(nextPageToken)
                }
            }
        })
    }

    override fun initProfileList(serviceData: YoutubeAdapterResponse) {
        vRecyclerViewProfiles.visibility = View.VISIBLE
        vEmptyListIcon.visibility = View.INVISIBLE

        nextPageToken = serviceData.nextPageToken
        profileList = mutableListOf()
        profileList.addAll(serviceData.listItem)

        viewAdapter = ProfileListAdapter(profileList) { item -> selectedItem(item) }

        vRecyclerViewProfiles.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        viewAdapter.notifyDataSetChanged()
    }

    override fun updateProfileList(serviceData: YoutubeAdapterResponse) {
        nextPageToken = serviceData.nextPageToken
        viewAdapter.addData(serviceData.listItem)
    }

    private fun selectedItem(item: YoutubeListItem) {
        presenter.onItemClickRequest(item)
    }

    override fun reproduceVideo(url: String) {
        // val intent = YouTubeStandalonePlayer.createVideoIntent(activity, API_KEY, url)
        // startActivity(intent)

        val sampleIntent = Intent(activity, YoutubeActivity::class.java)
        startActivity(sampleIntent)
    }

    override fun showEmptyData() {
        vRecyclerViewProfiles.visibility = View.INVISIBLE
        vEmptyListIcon.visibility = View.VISIBLE
    }

    override fun hideSoftKeyboard() {
        val imm: InputMethodManager = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var focus = activity?.currentFocus

        if (focus == null) {
            focus = View(activity)
        }

        imm.hideSoftInputFromWindow(focus.windowToken, 0)
    }

    override fun notifyNetworkCnxError() {
        Toast.makeText(context, getString(R.string.profile_network_error), Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val API_KEY = "AIzaSyAyr6Gc7wWjVxbK69nlxgVWyPVWrewV0_0"
        private const val PADDING_TO_REFRESH = 3
    }
}
