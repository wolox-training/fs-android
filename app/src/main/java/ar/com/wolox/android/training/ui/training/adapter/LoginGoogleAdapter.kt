package ar.com.wolox.android.training.ui.training.adapter

import android.content.Context
import ar.com.wolox.android.training.model.User
import ar.com.wolox.android.training.utils.CredentialsSession
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import java.lang.Exception
import javax.inject.Inject

class LoginGoogleAdapter @Inject constructor(
    private val credentialsSession: CredentialsSession,
    private val context: Context
) {

    fun checkLoggedUser(listener: LoginGoogleAdapterListener) {
        val account = GoogleSignIn.getLastSignedInAccount(context)

        if (account == null) {
            listener.onNullCredentials()
        } else if (account.id != credentialsSession.token ||
                account.email != credentialsSession.username) {
            listener.onExpiredCredentials()
        } else {
            val user = User(credentialsSession.username)
            user.token = credentialsSession.token
            listener.onLoggedUser(user)
        }
    }

    fun loginUser(completedTask: Task<GoogleSignInAccount>, listener: LoginGoogleAdapterListener) {
        try {

            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)!!
            val user: User = User(account.email)
            user.token = account.id

            val idUser: Int = addDecimalsOnNumber(account.id!!.toDouble())
            user.id = idUser
            listener.onLoggedUser(user)
        } catch (e: Exception) {
            listener.onError()
        }
    }

    fun addDecimalsOnNumber(input: Double): Int {
        // Sum all digits from the token to generate an user id (negative)
        // Example:: IN: 321 -> 3 + 2 + 1 = 6 -> OUT: -6
        var idToken: Double = input
        try {
            var idUserDouble = 0.0
            while (idToken > 0) {
                idUserDouble += (idToken % USER_ID_LIMIT)
                idToken /= USER_ID_LIMIT
            }

            val result: Int = idUserDouble.toInt()
            return -result
        } catch (e: Exception) {
            return 0
        }
    }

    companion object {
        private const val USER_ID_LIMIT = 100
    }
}