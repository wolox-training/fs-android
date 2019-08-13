package ar.com.wolox.android.trainingFS.ui.training.details

import android.view.WindowManager
import androidx.core.content.ContextCompat
import ar.com.wolox.android.R
import ar.com.wolox.wolmo.core.activity.WolmoActivity
import javax.inject.Inject

class DetailsActivity @Inject constructor() : WolmoActivity() {

    override fun layout(): Int = R.layout.activity_base

    override fun init() {
        this.window.run {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = ContextCompat.getColor(context, android.R.color.transparent)
            navigationBarColor = ContextCompat.getColor(context, android.R.color.transparent)
            setBackgroundDrawable(getDrawable(R.drawable.gradient_toolbar))
        }

        replaceFragment(R.id.vActivityBaseContent, DetailsFragment().apply { this.arguments = intent.extras })
    }
}