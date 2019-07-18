package ar.com.wolox.android.training.ui.viewpager.request

interface IRequestView {

    fun setNewsTitle(title: String)

    fun setNewsBody(body: String)

    fun showError()
}
