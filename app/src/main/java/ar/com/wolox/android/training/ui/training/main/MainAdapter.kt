package ar.com.wolox.android.training.ui.training.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ar.com.wolox.android.training.ui.training.main.mainTabs.news.NewsFragment
import ar.com.wolox.android.training.ui.training.main.mainTabs.ProfileFragment

class MainAdapter(fm: FragmentManager, private var totalTabs: Int) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> NewsFragment()
            1 -> ProfileFragment()
            else -> null
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}