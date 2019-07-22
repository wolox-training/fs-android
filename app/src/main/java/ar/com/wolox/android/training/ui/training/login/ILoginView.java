package ar.com.wolox.android.training.ui.training.login;

/**
 * ILoginView
 */
public interface ILoginView {

    void showEmptyEmailError();
    void showInvalidEmailError();
    void showEmptyPassError();

    void showValidEmail();
    void showValidPass();

    void onValidForm();
}
