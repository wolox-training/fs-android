package ar.com.wolox.android.training.ui.training.main

import androidx.viewpager.widget.ViewPager
import ar.com.wolox.android.R
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import com.google.android.material.tabs.TabLayout

class MainFragment : WolmoFragment<MainPresenter>(), IMainView {

    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null

    override fun layout(): Int = R.layout.fragment_main

    override fun init() {

        tabLayout = view?.findViewById(R.id.tab_layout)
        viewPager = view?.findViewById(R.id.pager)

        tabLayout!!.addTab(tabLayout!!.newTab().setText(getString(R.string.main_tab_news)).setIcon(R.drawable.ic_news_list_on))
        tabLayout!!.addTab(tabLayout!!.newTab().setText(getString(R.string.main_tab_profile)).setIcon(R.drawable.ic_profile_off))

        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = activity?.supportFragmentManager?.let { MainAdapter(it, tabLayout!!.tabCount) }
        viewPager!!.adapter = adapter

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        tab.setIcon(R.drawable.ic_news_list_on)
                    }
                    1 -> {
                        tab.setIcon(R.drawable.ic_profile_on)
                    }
                }
                viewPager!!.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        tab.setIcon(R.drawable.ic_news_list_off)
                    }
                    1 -> {
                        tab.setIcon(R.drawable.ic_profile_off)
                    }
                }
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })
    }
}