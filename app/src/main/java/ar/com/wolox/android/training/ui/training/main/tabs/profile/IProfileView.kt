package ar.com.wolox.android.training.ui.training.main.tabs.profile

import ar.com.wolox.android.training.model.ProfileItem

interface IProfileView {

    fun isNetworkAvailable(): Boolean

    fun updateProfileList(serviceData: List<ProfileItem>)
}