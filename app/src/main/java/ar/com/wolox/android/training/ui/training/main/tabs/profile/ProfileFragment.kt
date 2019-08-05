package ar.com.wolox.android.training.ui.training.main.tabs.profile

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.com.wolox.android.R
import ar.com.wolox.android.training.model.ProfileItem
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import com.facebook.drawee.backends.pipeline.Fresco
import kotlinx.android.synthetic.main.fragment_profile.vRecyclerViewProfiles
import kotlinx.android.synthetic.main.fragment_profile.vRefreshEmptyProfiles
import kotlinx.android.synthetic.main.fragment_profile.vRefreshListProfiles
import javax.inject.Inject

class ProfileFragment @Inject constructor() : WolmoFragment<ProfilePresenter>(), IProfileView {

    private lateinit var viewAdapter: ProfileListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var profileList: MutableList<ProfileItem>

    override fun layout(): Int = R.layout.fragment_profile

    override fun init() {
        Fresco.initialize(context)

        vRefreshListProfiles.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)
        vRefreshEmptyProfiles.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)

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

    override fun updateProfileList(serviceData: List<ProfileItem>) {
        vRefreshListProfiles.visibility = View.VISIBLE
        vRefreshEmptyProfiles.visibility = View.GONE

        profileList = mutableListOf()
        profileList.addAll(serviceData)

        viewAdapter = ProfileListAdapter(profileList) { item -> selectedItem(item) }

        vRecyclerViewProfiles.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        viewAdapter.notifyDataSetChanged()
    }

    private fun selectedItem(item: ProfileItem) {
        println("Click")
    }
}