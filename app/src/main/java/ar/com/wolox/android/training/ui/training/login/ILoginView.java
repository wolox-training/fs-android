package ar.com.wolox.android.training.ui.training.login;

import ar.com.wolox.android.training.model.User;

/**
 * ILoginView
 */
public interface ILoginView {

    void onEmptyEmailError();
    void onInvalidEmailError();
    void onEmptyPassError();

    void onValidEmail();
    void onValidPass();

    void onValidForm();

    void toSingUpScreen();

    void updateCredentials(User user);
}
