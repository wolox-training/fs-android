package ar.com.wolox.android.training.ui.example

import ar.com.wolox.android.R
import ar.com.wolox.wolmo.core.activity.WolmoActivity

class ExampleActivity : WolmoActivity() {

    override fun layout(): Int = R.layout.activity_base

    override fun init() {
        replaceFragment(R.id.vActivityBaseContent, ExampleFragment())
    }
}
