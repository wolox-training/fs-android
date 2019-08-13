package ar.com.wolox.android.trainingFS.ui.training.login;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import ar.com.wolox.android.trainingFS.model.User;
import ar.com.wolox.android.trainingFS.network.IUserService;
import ar.com.wolox.wolmo.networking.retrofit.RetrofitServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * LoginAdapter
 */
public class LoginAdapter {

    private RetrofitServices retrofitServices;

    @Inject
    LoginAdapter(RetrofitServices retrofitServices) {
        this.retrofitServices = retrofitServices;
    }

    /**
     * @param email email from login
     * @param password password from login
     * @param listener listener to handle service responses
     */
    void getUser(final String email, final String password, final ILoginAdapterListener listener) {
        Call<List<User>> response = retrofitServices.getService(IUserService.class).getUserRequest(email, password);
        response.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NotNull Call<List<User>> call, @NotNull Response<List<User>> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response.body() != null) {
                            List<User> users = response.body();
                            if (users.size() < 1) {
                                listener.onCredentialsError();
                            } else if (users.size() > 1) {
                                listener.onMultipleMatchError();
                            } else {
                                listener.onSuccess(users.get(0));
                            }
                        } else {
                            listener.onCredentialsError();
                        }

                    } catch (Exception e) {
                        listener.onError(e.getMessage());
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<User>> call, @NotNull Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }
}
