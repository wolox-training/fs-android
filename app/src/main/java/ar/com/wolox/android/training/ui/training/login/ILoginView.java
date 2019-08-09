package ar.com.wolox.android.training.ui.training.login;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

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

    void hideAnimations();
    void showCredentialsError();

    void updateCredentials(User user);

    void showInvalidCredentialsError();
    void showMultiplesCredentialsError();
    void showNetworkUnavailableError();
    void showServiceError(String message);

    void showSingUpScreen();
    void showTermsAndConditionWebView();
    void cleanCredentials();

    void showMainScreen();

    void showProgressDialog();
    void hideProgressDialog();

    boolean isNetworkAvailable();

    void loginWithGoogle(GoogleSignInClient client);
    void showLoginWithGoogleError();
}
