package ar.com.wolox.android.training.ui.training.login;

import android.text.TextUtils;
import android.util.Patterns;

import org.jetbrains.annotations.NotNull;
import java.util.List;

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

    private CredentialsSession userCredentials;
    private RetrofitServices retrofitServices;

    @Inject
    public LoginPresenter(CredentialsSession credentialsSession, RetrofitServices retrofitServices) {
        this.userCredentials = credentialsSession;
        this.retrofitServices = retrofitServices;
    }

    public void onInit() {

        // Get credentials from shared preferences, creates an user object (if exists) and update credentials
        if (userCredentials.getUsername() != null && userCredentials.getPassword() != null) {
            User user = new User(userCredentials.getUsername(), userCredentials.getPassword());
            getView().updateCredentials(user);
        }
    }

    /**
     *
     * @param user userId from login screen, must have valid format and cannot be empty
     * @param password passId from login screen, cannot be empty
     */
    public void onLoginButtonClicked(final CharSequence user, final CharSequence password) {
        boolean validForm = true;

        if (TextUtils.isEmpty(user)) {
            getView().showEmptyEmailError();
            validForm = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
            getView().showInvalidEmailError();
            validForm = false;
        } else {
            getView().showValidEmail();
        }

        if (TextUtils.isEmpty(password)) {
            getView().showEmptyPassError();
        } else {
            getView().showValidPass();

            if (validForm) {
                userCredentials.setUsername(user.toString());
                userCredentials.setPassword(password.toString());

                sendLoginRequest(user.toString(), password.toString());
            }
        }
    }

    public void onSingUpButtonClicked() {
        userCredentials.clearCredentials();
        getView().cleanCredentials();
        getView().showSingUpScreen();
    }

    public void onTermsAndConditionClicked() {
        getView().showTermsAndConditionWebView();
    }

    private void sendLoginRequest(final String username, final String password) {

        Call response = retrofitServices.getService(IUserService.class).getUserRequest(username, password);
        response.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NotNull Call<List<User>> call, @NotNull Response<List<User>> response) {
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
            public void onFailure(Call call, Throwable t) {
                getView().showServiceError(t.getMessage());
            }
        });
    }
}
