package ar.com.wolox.android.training.ui.training.main

import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import ar.com.wolox.android.R
import ar.com.wolox.android.training.ui.training.main.tabs.news.NewsFragment
import ar.com.wolox.android.training.ui.training.main.tabs.profile.ProfileFragment
import ar.com.wolox.wolmo.core.adapter.viewpager.SimpleFragmentPagerAdapter
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_viewpager.*
import javax.inject.Inject

class MainFragment : WolmoFragment<BasePresenter<Any>>() {

    @Inject internal lateinit var page1Fragment: NewsFragment
    @Inject internal lateinit var page2Fragment: ProfileFragment
    private lateinit var fragmentPagerAdapter: SimpleFragmentPagerAdapter

    var tabLayout: TabLayout? = null

    override fun layout(): Int = R.layout.fragment_main

    override fun init() {
        fragmentPagerAdapter = SimpleFragmentPagerAdapter(childFragmentManager)
        fragmentPagerAdapter.addFragments(
                Pair<Fragment, String>(page1Fragment, getString(R.string.main_first_page)),
                Pair<Fragment, String>(page2Fragment, getString(R.string.main_second_page)))
        vViewPager.adapter = fragmentPagerAdapter

        tabLayout = view?.findViewById(R.id.tab_layout)
        vViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0) {
                    tab.setIcon(R.drawable.ic_news_list_on)
                } else {
                    tab.setIcon(R.drawable.ic_profile_on)
                }
                vViewPager!!.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
                if (tab.position == 0) {
                    tab.setIcon(R.drawable.ic_news_list_off)
                } else {
                    tab.setIcon(R.drawable.ic_profile_off)
                }
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })
    }
}