package ar.com.wolox.android.training.ui.training.login;

import android.os.Handler;

import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import ar.com.wolox.android.training.model.User;
import ar.com.wolox.android.training.network.IUserService;
import ar.com.wolox.android.training.utils.CredentialsSession;
import ar.com.wolox.wolmo.core.presenter.BasePresenter;
import ar.com.wolox.wolmo.networking.retrofit.RetrofitServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * LoginPresenter
 */
public class LoginPresenter extends BasePresenter<ILoginView> {

    private static final long DELAY = 5000L;

    private CredentialsSession userCredentials;
    private RetrofitServices retrofitServices;

    @Inject
    public LoginPresenter(CredentialsSession credentialsSession, RetrofitServices retrofitServices) {
        this.userCredentials = credentialsSession;
        this.retrofitServices = retrofitServices;
    }

    /**
     * onInit(): replace onViewAttached method because its calls after fragment init
     */
    void onInit() {
        // Get credentials from shared preferences, creates an user object (if exists) and update credentials
        if (userCredentials.getUsername() != null && userCredentials.getPassword() != null) {
            User user = new User(userCredentials.getUsername(), userCredentials.getPassword());
            getView().updateCredentials(user);
            getView().hideAnimations();
            getView().showMainScreen();
        } else {
            new Handler().postDelayed(() -> getView().hideAnimations(), DELAY);
        }
    }

    /**
     *
     * @param user userId from login screen, must have valid format and cannot be empty
     * @param password passId from login screen, cannot be empty
     */
    public void onLoginButtonClicked(final String user, final String password) {
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
                userCredentials.setUsername(user);
                userCredentials.setPassword(password);
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
            Call<List<User>> response = retrofitServices.getService(IUserService.class).getUserRequest(username, password);
            response.enqueue(new Callback<List<User>>() {

                @Override
                public void onResponse(@NotNull Call<List<User>> call, @NotNull Response<List<User>> response) {
                    getView().hideProgressDialog();
                    if (response.isSuccessful()) {
                        try {
                            if (response.body() != null) {
                                List<User> users = response.body();
                                if (users.size() < 1) {
                                    getView().showInvalidCredentialsError();
                                } else if (users.size() > 1) {
                                    getView().showMultiplesCredentialsError();
                                } else {
                                    getView().showMainScreen();
                                }
                            } else {
                                getView().showInvalidCredentialsError();
                            }

                        } catch (Exception e) {
                            getView().showServiceError(e.getMessage());
                        }
                    } else {
                        getView().showServiceError(response.message());
                    }
                }

                @Override
                public void onFailure(@NotNull Call call, @NotNull Throwable t) {
                    getView().hideProgressDialog();
                    getView().showServiceError(t.getMessage());
                }
            });
        }
    }
}
