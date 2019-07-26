package ar.com.wolox.android.training.ui.training.main

import android.view.WindowManager
import ar.com.wolox.android.R
import ar.com.wolox.wolmo.core.activity.WolmoActivity

class MainActivity : WolmoActivity() {

    override fun layout(): Int = R.layout.activity_base

    override fun init() {

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(android.R.color.transparent)
        window.navigationBarColor = resources.getColor(android.R.color.transparent)
        window.setBackgroundDrawable(getDrawable(R.drawable.gradient_toolbar))

        replaceFragment(R.id.vActivityBaseContent, MainFragment())
    }
}