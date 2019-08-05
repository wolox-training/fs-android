package ar.com.wolox.android.training.ui.training.main.tabs.profile

import ar.com.wolox.android.training.model.ProfileItem
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import javax.inject.Inject

class ProfilePresenter @Inject constructor() : BasePresenter<IProfileView>() {

    fun onInit() {
        val sampleList = mutableListOf<ProfileItem>()
        val sampleItem = ProfileItem(
                "NextSample",
                "PrevSample",
                5,
                mutableListOf()
        )
        sampleList.add(sampleItem)

        view.updateProfileList(sampleList)
    }

    fun onSearchRequest(query: String) {
        view.reproduceVideo("2ZBtPf7FOoM")
    }
}