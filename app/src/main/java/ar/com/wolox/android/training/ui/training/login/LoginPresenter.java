package ar.com.wolox.android.training.ui.training.login;

import android.text.TextUtils;
import android.util.Patterns;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import ar.com.wolox.android.training.model.User;
import ar.com.wolox.android.training.model.UserDTO;
import ar.com.wolox.android.training.network.IRest;
import ar.com.wolox.android.training.utils.CredentialsSession;
import ar.com.wolox.wolmo.core.presenter.BasePresenter;
import ar.com.wolox.wolmo.networking.retrofit.RetrofitServices;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * LoginPresenter
 */
public class LoginPresenter extends BasePresenter<ILoginView> {

    private CredentialsSession userCredentials;
    private RetrofitServices retrofitServices;

    private final String mapKey = "application/json";

    @Inject
    public LoginPresenter(CredentialsSession credentialsSession, RetrofitServices retrofitServices) {
        this.userCredentials = credentialsSession;
        this.retrofitServices = retrofitServices;

        //retrofitServices.getService(IRest.class).getUserRequest()
    }

    /**
     *
     * @param user userId from login screen, must have valid format and cannot be empty
     * @param password passId from login screen, cannot be empty
     */
    public void onLoginButtonClicked(final CharSequence user, final CharSequence password) {
        boolean validForm = true;

        if (TextUtils.isEmpty(user)) {
            getView().onEmptyEmailError();
            validForm = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
            getView().onInvalidEmailError();
            validForm = false;
        } else {
            getView().onValidEmail();
        }

        if (TextUtils.isEmpty(password)) {
            getView().onEmptyPassError();
        } else {
            getView().onValidPass();

            if (validForm) {
                userCredentials.setUsername(user.toString());
                userCredentials.setPassword(password.toString());

                sendLoginRequest(user.toString(), password.toString());
            }
        }
    }

    public User loadCredentials() {
        User user = new User();

        user.user = userCredentials.getUsername();
        user.pass = userCredentials.getPassword();
        return user;
    }

    public void onSingUpButtonClicked() {
        userCredentials.clearCredentials();
        getView().toSingUpScreen();
    }

    private void sendLoginRequest(String username, String password) {
        //getView().onValidForm();

        UserDTO userDTO = new UserDTO();
        userDTO.email = username;
        userDTO.password = password;

        Gson gson = new Gson();
        String sLoginData = gson.toJson(userDTO);
        final RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), sLoginData);

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", mapKey);
        headerMap.put("Accept", mapKey);

        Call response = retrofitServices.getService(IRest.class).getUserRequest(headerMap, username);
        response.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                getView().onValidForm();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                getView().onValidForm();
            }
        });

    }
}
