package ar.com.wolox.android.training.ui.training.login;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

import javax.inject.Inject;

import ar.com.wolox.android.training.model.User;
import ar.com.wolox.android.training.utils.CredentialsSession;

/**
 * LoginGoogleAdapter
 */
class LoginGoogleAdapter {

    private static final int USER_ID_LIMIT = 100;

    private CredentialsSession userCredentials;

    @Inject
    LoginGoogleAdapter(CredentialsSession session) {
        userCredentials = session;
    }

    void checkLoggedUser(final GoogleSignInAccount account, final ILoginGoogleAdapterListener listener) {
        if (account == null) {
            listener.onNullCredentials();
        } else if (!Objects.equals(account.getId(), userCredentials.getToken()) ||
                !Objects.equals(account.getEmail(), userCredentials.getUsername())) {
            listener.onExpiredCredentials();
        } else {
            User user = new User(userCredentials.getUsername());
            user.setToken(userCredentials.getToken());
            listener.onLoggedUser(user);
        }
    }

    void loginUser(final Task<GoogleSignInAccount> completedTask, final ILoginGoogleAdapterListener listener) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account == null) {
                listener.onNullCredentials();
            } else {
                User user = new User(account.getEmail());
                user.setToken(account.getId());

                int idUser = addDecimalsOfNumber(Double.parseDouble(Objects.requireNonNull(account.getId())));
                user.setId(idUser);

                listener.onLoggedUser(user);
            }
        } catch (ApiException e) {
            listener.onError();
        }
    }

    private int addDecimalsOfNumber(double idToken) {
        //Sum all digits from the token to generate an user id (negative)
        //Example:: IN: 321 -> 3 + 2 + 1 = 6 -> OUT: -6
        try {
            double idUserDouble = 0d;
            while (idToken > 0) {
                idUserDouble = idUserDouble + (idToken % USER_ID_LIMIT);
                idToken = idToken / USER_ID_LIMIT;
            }
            int result = (int) idUserDouble;

            return -result;
        } catch (Exception e) {
            return 0;
        }
    }
}
