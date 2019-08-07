package ar.com.wolox.android.training.ui.training.main.tabs.profile

import android.Manifest
import android.accounts.AccountManager
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.com.wolox.android.R
import ar.com.wolox.android.training.model.ProfileItem
import ar.com.wolox.android.training.utils.onClickListener
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.youtube.player.YouTubeStandalonePlayer
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import kotlinx.android.synthetic.main.fragment_profile.vRecyclerViewProfiles
import kotlinx.android.synthetic.main.fragment_profile.vRefreshEmptyProfiles
import kotlinx.android.synthetic.main.fragment_profile.vRefreshListProfiles
import kotlinx.android.synthetic.main.fragment_profile.vSearch
import kotlinx.android.synthetic.main.fragment_profile.vSearchQuery
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

class ProfileFragment @Inject constructor() : WolmoFragment<ProfilePresenter>(), IProfileView {

    private lateinit var viewAdapter: ProfileListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var profileList: MutableList<ProfileItem>
    private lateinit var googleCredential: GoogleAccountCredential

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

    override fun setListeners() {
        vSearch.onClickListener {
            presenter.onSearchRequest(vSearchQuery.text.toString())
        }
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

    override fun reproduceVideo(url: String) {
        // TODO: Reproducir video de youtube
        val intent = YouTubeStandalonePlayer.createVideoIntent(activity, API_KEY, url)
        startActivity(intent)

        // TODO: Verificar
        // val scopes: Collection<String> = listOf(YouTubeScopes.YOUTUBE_READONLY)

        // googleCredential = GoogleAccountCredential.usingOAuth2(context, scopes)
        // googleCredential.backOff = ExponentialBackOff()

        // getResultsFromApi()

        // TODO TestActivity!!
        // val intent = Intent(activity, TestActivity::class.java)
        // startActivity(intent)
    }

    private fun getResultsFromApi() {
        if (!isGooglePlayServicesAvailable()) {
            acquireGooglePlayService()
        } else if (googleCredential.selectedAccountName == null) {
            chooseAccount()
        } else {
            MakeRequestTask().execute(googleCredential)
        }
    }

    private fun isGooglePlayServicesAvailable(): Boolean {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(context)
        return connectionStatusCode == ConnectionResult.SUCCESS
    }

    private fun acquireGooglePlayService() {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(context)
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            Toast.makeText(context, "ERROR: $connectionStatusCode", Toast.LENGTH_SHORT).show()
        }
    }

    private fun chooseAccount() {
        if (EasyPermissions.hasPermissions(context!!, Manifest.permission.GET_ACCOUNTS)) {
            startActivityForResult(googleCredential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER)
        } else {
            activity?.let {
                EasyPermissions.requestPermissions(it,
                        "This app needs to access your Google account (via Contacts).",
                        REQUEST_PERMISSION_GET_ACCOUNTS,
                        Manifest.permission.GET_ACCOUNTS)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_GOOGLE_PLAY_SERVICES -> {
                if (resultCode != RESULT_OK) {
                    Toast.makeText(context, "This app requires Google Play Services", Toast.LENGTH_SHORT).show()
                } else {
                    getResultsFromApi()
                }
            }
            REQUEST_ACCOUNT_PICKER -> {
                if (resultCode == RESULT_OK && data != null && data.extras != null) {
                    val accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
                    if (accountName != null) {
                        googleCredential.selectedAccountName = accountName
                        getResultsFromApi()
                    }
                }
            }
            REQUEST_AUTHORIZATION -> {
                if (resultCode == RESULT_OK) {
                    getResultsFromApi()
                }
            }
        }
    }

    companion object {
        private const val API_KEY = "AIzaSyAyr6Gc7wWjVxbK69nlxgVWyPVWrewV0_0"
        private const val REQUEST_ACCOUNT_PICKER = 1000
        private const val REQUEST_AUTHORIZATION = 1001
        private const val REQUEST_GOOGLE_PLAY_SERVICES = 1002
        private const val REQUEST_PERMISSION_GET_ACCOUNTS = 1003
    }

    class MakeRequestTask : AsyncTask<GoogleAccountCredential, Void, List<String>>() {

        private lateinit var service: YouTube

        private fun onPreExecute(googleCredential: GoogleAccountCredential) {
            val transport = AndroidHttp.newCompatibleTransport()
            val jsonFactory = JacksonFactory.getDefaultInstance()
            service = YouTube.Builder(transport, jsonFactory, googleCredential)
                    .setApplicationName("API YOUTUBE").build()
        }

        override fun doInBackground(vararg p0: GoogleAccountCredential?): List<String> {
            val credential = p0[0]!!
            onPreExecute(credential)

            return try {
                getDataFromApi()
            } catch (e: Exception) {
                emptyList()
            }
        }

        private fun getDataFromApi(): List<String> {
            val channelInfo = ArrayList<String>()

            val serviceChannel = service.channels()
            val serviceList = serviceChannel.list("snippet,contentDetails,statistics")
            serviceList.forUsername = "GoogleDevelopers"
            serviceList.maxResults = 5
            serviceList.set("q", "System")
            val result = serviceList.execute()

            val channels = result.items
            if (channels != null) {
                val channel = channels[0]
                channelInfo.add("CHANNEL ID: " + channel.id + "." + "TITLE: " + channel.snippet.title +
                        ", " + "VIEWS: " + channel.statistics.viewCount)
            }
            return channelInfo
        }

        override fun onPostExecute(result: List<String>?) {
            if (result == null || result.isEmpty()) {
                println("EMPTY OR NULL")
            } else {
                println("SUCCESS")
            }
        }
    }
}
