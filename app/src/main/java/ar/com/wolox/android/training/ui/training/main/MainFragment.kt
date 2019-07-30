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
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_viewpager.vViewPager
import javax.inject.Inject

class MainFragment : WolmoFragment<BasePresenter<Any>>() {

    @Inject internal lateinit var page1Fragment: NewsFragment
    @Inject internal lateinit var page2Fragment: ProfileFragment
    private lateinit var fragmentPagerAdapter: SimpleFragmentPagerAdapter

    override fun layout(): Int = R.layout.fragment_main

    override fun init() {
        fragmentPagerAdapter = SimpleFragmentPagerAdapter(childFragmentManager)
        fragmentPagerAdapter.addFragments(
                Pair<Fragment, String>(page1Fragment, getString(R.string.main_first_page)),
                Pair<Fragment, String>(page2Fragment, getString(R.string.main_second_page)))
        vViewPager.adapter = fragmentPagerAdapter

        vViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(vTabLayout))
        vTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.setIcon(if (tab.position == 0) R.drawable.ic_news_list_on else R.drawable.ic_profile_on)
                vViewPager!!.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.setIcon(if (tab.position == 0) R.drawable.ic_news_list_off else R.drawable.ic_profile_off)
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })
    }
}