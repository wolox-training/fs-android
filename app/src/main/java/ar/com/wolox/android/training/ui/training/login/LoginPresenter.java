package ar.com.wolox.android.training.ui.training.login;

import android.text.TextUtils;
import android.util.Patterns;

import javax.inject.Inject;

import ar.com.wolox.android.training.model.User;
import ar.com.wolox.android.training.utils.CredentialsSession;
import ar.com.wolox.wolmo.core.presenter.BasePresenter;

/**
 * LoginPresenter
 */
public class LoginPresenter extends BasePresenter<ILoginView> {

    private CredentialsSession userCredentials;

    @Inject
    public LoginPresenter(CredentialsSession credentialsSession) {
        userCredentials = credentialsSession;
    }

    public void onInit() {

        // Get credentials from shared preferences, creates an user object (if exists) and update credentials
        if (userCredentials.getUsername() != null && userCredentials.getPassword() != null) {
            User user = new User(userCredentials.getUsername(), userCredentials.getPassword());
            getView().updateCredentials(user);
        }
    }

    /**
     *
     * @param user userId from login screen, must have valid format and cannot be empty
     * @param password passId from login screen, cannot be empty
     */
    public void onLoginButtonClicked(final CharSequence user, final CharSequence password) {
        boolean validForm = true;

        if (TextUtils.isEmpty(user)) {
            getView().showEmptyEmailError();
            validForm = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
            getView().showInvalidEmailError();
            validForm = false;
        } else {
            getView().showValidEmail();
        }

        if (TextUtils.isEmpty(password)) {
            getView().showEmptyPassError();
        } else {
            getView().showValidPass();

            if (validForm) {
                userCredentials.setUsername(user.toString());
                userCredentials.setPassword(password.toString());
                getView().showMainScreen();
            }
        }
    }

    public void onSingUpButtonClicked() {
        userCredentials.clearCredentials();
        getView().cleanCredentials();
        getView().showSingUpScreen();
    }

    public void onTermsAndConditionClicked() {
        getView().showTermsAndConditionWebView();
    }
}
