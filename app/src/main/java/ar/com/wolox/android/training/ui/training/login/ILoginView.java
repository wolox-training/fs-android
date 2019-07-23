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

    void toSingUpScreen();

    void toMainScreen();
    void showCredentialsError(int errorCode, String message);
}
