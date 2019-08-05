package ar.com.wolox.android.training.ui.training.login;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

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

    void loginWithGoogle();
    void showLoginWithGoogleError();
    GoogleSignInAccount getSignedUser();
}
