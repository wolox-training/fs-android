package ar.com.wolox.android.training.ui.training.login;

import ar.com.wolox.android.training.model.User;

/**
 * ILoginView
 */
public interface ILoginView {

    void showEmptyEmailError();
    void showInvalidEmailError();
    void showEmptyPassError();

    void showValidEmail();
    void showValidPass();

    void updateCredentials(User user);

    void showSingUpScreen();
    void showTermsAndConditionWebView();
    void cleanCredentials();

    void showMainScreen();
}
