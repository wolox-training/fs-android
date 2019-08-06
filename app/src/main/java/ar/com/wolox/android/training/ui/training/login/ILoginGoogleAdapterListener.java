package ar.com.wolox.android.training.ui.training.login;

import ar.com.wolox.android.training.model.User;

/**
 * ILoginGoogleAdapterListener
 */
public interface ILoginGoogleAdapterListener {

    void onNullCredentials();
    void onExpiredCredentials();
    void onLoggedUser(User user);
    void onError();
}
