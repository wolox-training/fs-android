package ar.com.wolox.android.training.ui.training.login;

import android.text.TextUtils;
import android.util.Patterns;

import javax.inject.Inject;

import ar.com.wolox.android.training.utils.UserSession;
import ar.com.wolox.wolmo.core.presenter.BasePresenter;

/**
 * LoginPresenter
 */
public class LoginPresenter extends BasePresenter<ILoginView> {

    @Inject
    public LoginPresenter(UserSession userSession) {}

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
                getView().onValidForm();
            }
        }
    }
}
