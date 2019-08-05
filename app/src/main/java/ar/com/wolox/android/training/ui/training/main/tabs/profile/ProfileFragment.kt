package ar.com.wolox.android.training.ui.training.main.tabs.profile

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import ar.com.wolox.android.R
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import kotlinx.android.synthetic.main.fragment_profile.vRefreshEmptyList
import kotlinx.android.synthetic.main.fragment_profile.vRefreshListLayout
import javax.inject.Inject

class ProfileFragment @Inject constructor() : WolmoFragment<ProfilePresenter>(), IProfileView {

    override fun layout(): Int = R.layout.fragment_profile

    override fun init() {
        vRefreshListLayout.visibility = View.GONE
        vRefreshEmptyList.visibility = View.VISIBLE
    }

    override fun isNetworkAvailable(): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }
}