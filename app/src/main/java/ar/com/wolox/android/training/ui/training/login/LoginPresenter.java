package ar.com.wolox.android.training.ui.training.login;

import android.os.Handler;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import ar.com.wolox.android.training.model.User;
import ar.com.wolox.android.training.utils.CredentialsSession;
import ar.com.wolox.wolmo.core.presenter.BasePresenter;

/**
 * LoginPresenter
 */
public class LoginPresenter extends BasePresenter<ILoginView> {

    private static final long DELAY = 5000L;
    private static final int RC_SIGN_IN = 10;

    private CredentialsSession userCredentials;
    private LoginAdapter loginAdapter;

    @Inject
    public LoginPresenter(CredentialsSession credentialsSession, LoginAdapter loginAdapter) {
        this.userCredentials = credentialsSession;
        this.loginAdapter = loginAdapter;
    }

    /**
     * onInit(): replace onViewAttached method because its calls after fragment init
     */
    void onInit() {
        // Get credentials from shared preferences, creates an title object (if exists) and update credentials
        if (userCredentials.getUsername() != null && userCredentials.getPassword() != null) {
            User user = new User(userCredentials.getUsername(), userCredentials.getPassword());
            getView().updateCredentials(user);
            getView().hideAnimations();
            getView().showMainScreen();

        } else {
            //Check login from google, and do autologin
            GoogleSignInAccount account = getView().getSignedUser();
            if (Objects.equals(account.getId(), userCredentials.getToken()) &&
                    Objects.equals(account.getEmail(), userCredentials.getUsername())) {
                User user = new User(userCredentials.getUsername(), "");
                user.setToken(userCredentials.getToken());
                getView().updateCredentials(user);
                getView().hideAnimations();
                getView().showMainScreen();
            } else {
                new Handler().postDelayed(() -> getView().hideAnimations(), DELAY);
            }
        }

    }

    /**
     *
     * @param user userId from login screen, must have valid format and cannot be empty
     * @param password passId from login screen, cannot be empty
     */
    void onLoginButtonClicked(final String user, final String password) {
        boolean validForm = true;

        if (user.isEmpty()) {
            validForm = false;
            getView().showEmptyEmailError();
        } else if (!isValidEmail(user)) {
            validForm = false;
            getView().showInvalidEmailError();
        } else {
            getView().showValidEmail();
        }

        if (password.isEmpty()) {
            getView().showEmptyPassError();
        } else {
            getView().showValidPass();

            if (validForm) {
                sendLoginRequest(user, password);
            }
        }
    }

    private boolean isValidEmail(String str) {
        // This method replace Patterns.EMAIL_ADDRESS.matcher(target).matches()); because throws
        // an exception in unit test (NullPointerException)
        String expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    void onSingUpButtonClicked() {
        userCredentials.clearCredentials();
        getView().cleanCredentials();
        getView().showSingUpScreen();
    }

    void onTermsAndConditionClicked() {
        getView().showTermsAndConditionWebView();
    }

    private void sendLoginRequest(final String username, final String password) {

        getView().showProgressDialog();
        if (!getView().isNetworkAvailable()) {
            getView().hideProgressDialog();
            getView().showNetworkUnavailableError();
        } else {
            loginAdapter.getUser(username, password, new ILoginAdapterListener() {
                @Override
                public void onFailure(String msg) {
                    getView().hideProgressDialog();
                    getView().showServiceError(msg);
                }

                @Override
                public void onError(String msg) {
                    getView().hideProgressDialog();
                    getView().showServiceError(msg);
                }

                @Override
                public void onCredentialsError() {
                    getView().hideProgressDialog();
                    getView().showInvalidCredentialsError();
                }

                @Override
                public void onMultipleMatchError() {
                    getView().hideProgressDialog();
                    getView().showMultiplesCredentialsError();
                }

                @Override
                public void onSuccess(User user) {
                    getView().hideProgressDialog();
                    userCredentials.clearCredentials();
                    userCredentials.setUsername(user.getEmail());
                    userCredentials.setPassword(user.getPassword());
                    userCredentials.setId(user.getId());
                    getView().showMainScreen();
                }
            });
        }
    }

    void onGoogleLoginRequest() {
        getView().loginWithGoogle();
    }

    void onActivityResult(final int requestCode, final Task<GoogleSignInAccount> task) {
        getView().showProgressDialog();
        if (requestCode == RC_SIGN_IN) {
            handleSignInResult(task);
        } else {
            getView().hideProgressDialog();
            getView().showLoginWithGoogleError();
        }
    }

    private void handleSignInResult(final Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                getView().hideProgressDialog();
                userCredentials.clearCredentials();
                userCredentials.setUsername(account.getEmail());
                userCredentials.setToken(account.getId());
                getView().showMainScreen();
            } else {
                getView().showLoginWithGoogleError();
            }
        } catch (ApiException e) {
            getView().hideProgressDialog();
            getView().showLoginWithGoogleError();
        }
    }
}
