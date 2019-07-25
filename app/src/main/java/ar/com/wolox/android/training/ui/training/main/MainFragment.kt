package ar.com.wolox.android.training.ui.training.main

import ar.com.wolox.android.R
import ar.com.wolox.wolmo.core.fragment.WolmoFragment

class MainFragment : WolmoFragment<MainPresenter>(), IMainView {

    override fun layout(): Int = R.layout.fragment_main

    override fun init() {
    }
}