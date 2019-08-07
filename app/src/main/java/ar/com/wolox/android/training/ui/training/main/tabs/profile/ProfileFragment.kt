package ar.com.wolox.android.training.ui.training.main.tabs.profile

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.com.wolox.android.R
import ar.com.wolox.android.training.model.youtube.YoutubeAdapterResponse
import ar.com.wolox.android.training.model.youtube.YoutubeListItem
import ar.com.wolox.android.training.utils.onClickListener
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.android.youtube.player.YouTubeStandalonePlayer
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

class ProfileFragment @Inject constructor() : WolmoFragment<ProfilePresenter>(), IProfileView {

    private lateinit var viewAdapter: ProfileListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var profileList: MutableList<YoutubeListItem>

    override fun layout(): Int = R.layout.fragment_profile

    override fun init() {
        Fresco.initialize(context)

        vRefreshListProfiles.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)
        vRefreshEmptyProfiles.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)

        vRefreshListProfiles.isRefreshing = false
        vRefreshEmptyProfiles.isRefreshing = false

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

    override fun setListeners() {
        vSearch.onClickListener {
            presenter.onSearchRequest(vSearchQuery.text.toString())
        }

        vRefreshListProfiles.setOnRefreshListener {
            vRefreshListProfiles.isRefreshing = false
        }

        vRefreshEmptyProfiles.setOnRefreshListener {
            vRefreshEmptyProfiles.isRefreshing = false
        }
    }

    override fun updateProfileList(serviceData: YoutubeAdapterResponse) {
        vRefreshListProfiles.visibility = View.VISIBLE
        vRefreshEmptyProfiles.visibility = View.INVISIBLE

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

    private fun selectedItem(item: YoutubeListItem) {
        presenter.onItemClickRequest(item)
    }

    override fun reproduceVideo(url: String) {
        val intent = YouTubeStandalonePlayer.createVideoIntent(activity, API_KEY, url)
        startActivity(intent)
    }

    override fun showEmptyData() {
        vRefreshListProfiles.visibility = View.INVISIBLE
        vRefreshEmptyProfiles.visibility = View.VISIBLE
    }

    override fun hideSoftKeyboard() {
        val imm: InputMethodManager = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var focus = activity?.currentFocus

        if (focus == null) {
            focus = View(activity)
        }

        imm.hideSoftInputFromWindow(focus.windowToken, 0)
    }

    companion object {
        private const val API_KEY = "AIzaSyAyr6Gc7wWjVxbK69nlxgVWyPVWrewV0_0"
    }
}
