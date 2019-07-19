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

    /**
     *
     * @param user userId from login screen, must have valid format and cannot be empty
     * @param password passId from login screen, cannot be empty
     */
    public void onLoginButtonClicked(final CharSequence user, final CharSequence password) {
        boolean validForm = true;

        if (TextUtils.isEmpty(user)) {
            getView().onEmptyEmailError();
            validForm = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
            getView().onInvalidEmailError();
            validForm = false;
        } else {
            getView().onValidEmail();
        }

        if (TextUtils.isEmpty(password)) {
            getView().onEmptyPassError();
        } else {
            getView().onValidPass();

            if (validForm) {
                userCredentials.setUsername(user.toString());
                userCredentials.setPassword(password.toString());

                getView().onValidForm();
            }
        }
    }

    public User loadCredentials() {
        User user = new User();

        user.user = userCredentials.getUsername();
        user.pass = userCredentials.getPassword();
        return user;
    }

    public void onSingUpButtonClicked() {
        userCredentials.clearCredentials();
        getView().toSingUpScreen();
    }
}
