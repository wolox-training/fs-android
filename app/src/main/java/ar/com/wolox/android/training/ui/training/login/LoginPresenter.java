package ar.com.wolox.android.training.ui.training.login;

import android.os.Handler;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import ar.com.wolox.android.training.model.User;
import ar.com.wolox.android.training.ui.training.adapter.LoginGoogleAdapterListener;
import ar.com.wolox.android.training.utils.CredentialsSession;
import ar.com.wolox.wolmo.core.presenter.BasePresenter;
import ar.com.wolox.android.training.ui.training.adapter.LoginGoogleAdapter;

/**
 * LoginPresenter
 */
public class LoginPresenter extends BasePresenter<ILoginView> {

    private static final long DELAY = 5000L;
    private static final int RC_SIGN_IN = 10;

    private CredentialsSession userCredentials;
    private LoginAdapter loginAdapter;
    private LoginGoogleAdapter googleAdapter;

    @Inject
    public LoginPresenter(CredentialsSession credentialsSession,
                          LoginAdapter loginAdapter,
                          LoginGoogleAdapter adapter) {
        this.userCredentials = credentialsSession;
        this.loginAdapter = loginAdapter;
        this.googleAdapter = adapter;
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
            googleAdapter.checkLoggedUser(new LoginGoogleAdapterListener() {
                @Override
                public void onNullCredentials() {
                    new Handler().postDelayed(() -> getView().hideAnimations(), DELAY);
                }

                @Override
                public void onExpiredCredentials() {
                    new Handler().postDelayed(() -> {
                        getView().hideAnimations();
                        getView().showCredentialsError();
                    }, DELAY);
                }

                @Override
                public void onLoggedUser(@NotNull User user) {
                    getView().updateCredentials(user);
                    getView().hideAnimations();
                    getView().showMainScreen();
                }

                @Override
                public void onError() {
                    new Handler().postDelayed(() -> getView().hideAnimations(), DELAY);
                }
            });
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
                    userCredentials.setToken(user.getToken());
                    userCredentials.setId(user.getId());
                    getView().showMainScreen();
                }
            });
        }
    }

    void onGoogleLoginRequest() {
        if (!getView().isNetworkAvailable()) {
            getView().showNetworkUnavailableError();
        } else {
            getView().loginWithGoogle();
        }
    }

    void onActivityResult(final int requestCode, final Task<GoogleSignInAccount> task) {
        getView().showProgressDialog();
        if (requestCode == RC_SIGN_IN) {
            googleAdapter.loginUser(task, new LoginGoogleAdapterListener() {
                @Override
                public void onNullCredentials() {
                    getView().hideProgressDialog();
                    getView().showLoginWithGoogleError();
                }

                @Override
                public void onExpiredCredentials() {
                    getView().hideProgressDialog();
                    getView().showLoginWithGoogleError();
                }

                @Override
                public void onLoggedUser(@NotNull User user) {
                    getView().hideProgressDialog();
                    userCredentials.clearCredentials();
                    userCredentials.setUsername(user.getEmail());
                    userCredentials.setToken(user.getToken());
                    userCredentials.setId(user.getId());
                    getView().showMainScreen();
                }

                @Override
                public void onError() {
                    getView().hideProgressDialog();
                    getView().showLoginWithGoogleError();
                }
            });
        } else {
            getView().hideProgressDialog();
            getView().showLoginWithGoogleError();
        }
    }
}
