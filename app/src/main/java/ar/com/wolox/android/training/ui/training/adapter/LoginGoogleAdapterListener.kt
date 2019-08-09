package ar.com.wolox.android.training.ui.training.adapter

import ar.com.wolox.android.training.model.User

interface LoginGoogleAdapterListener {

    fun onNullCredentials()
    fun onExpiredCredentials()
    fun onLoggedUser(user: User)
    fun onError()
}