package ar.com.wolox.android.training.ui.training.login;

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
}
