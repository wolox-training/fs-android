package ar.com.wolox.android.training.ui.training.login;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import ar.com.wolox.android.R;
import ar.com.wolox.android.training.model.User;
import ar.com.wolox.android.training.network.IRest;
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

    private static final int ERROR_CODE = 400;

    private CredentialsSession userCredentials;
    private RetrofitServices retrofitServices;

    private final String mapKey = "application/json";

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
     * @param ctx context to get string resources and show messages
     */
    public void onLoginButtonClicked(final CharSequence user, final CharSequence password, final Context ctx) {
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

                sendLoginRequest(user.toString(), password.toString(), ctx);
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

    private void sendLoginRequest(final String username, final String password, final Context ctx) {

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", mapKey);
        headerMap.put("Accept", mapKey);

        Call response = retrofitServices.getService(IRest.class).getUserRequest(headerMap, username, password);
        response.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    try {
                        if (response.body() != null) {
                            JSONArray jsonArray = new JSONArray(response.body().toString());
                            if (jsonArray.length() < 1) {
                                getView().showCredentialsError(ERROR_CODE, ctx.getString(R.string.error_wrong_credentials));
                            } else if (jsonArray.length() > 1) {
                                getView().showCredentialsError(ERROR_CODE, ctx.getString(R.string.error_multiple_response));
                            } else {
                                getView().showMainScreen();
                            }
                        } else {
                            getView().showCredentialsError(ERROR_CODE, ctx.getString(R.string.error_wrong_credentials));
                        }

                    } catch (Exception e) {
                        getView().showCredentialsError(ERROR_CODE, e.getMessage());
                    }
                } else {
                    getView().showCredentialsError(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                getView().showCredentialsError(ERROR_CODE, t.getMessage());
            }
        });
    }
}
