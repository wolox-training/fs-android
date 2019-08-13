package ar.com.wolox.android.trainingFS.ui.training.adapter

import ar.com.wolox.android.trainingFS.model.User

interface LoginGoogleAdapterListener {

    fun onNullCredentials()
    fun onExpiredCredentials()
    fun onLoggedUser(user: User)
    fun onError()
}