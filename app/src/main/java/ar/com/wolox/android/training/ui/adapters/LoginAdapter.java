package ar.com.wolox.android.training.ui.adapters;

import java.util.List;

import javax.inject.Inject;

import ar.com.wolox.android.training.model.User;
import retrofit2.Response;

/**
 * LoginAdapter
 */
public class LoginAdapter {

    @Inject
    LoginAdapter() {

    }

    /**
     * @param response API Rest response
     * @param listener Adapter listener with possibles responses
     */
    public void getUser(Response<List<User>> response, ILoginAdapterListener listener) {
        if (response.isSuccessful()) {
            try {
                if (response.body() != null) {
                    List<User> users = response.body();
                    if (users.size() < 1) {
                        listener.onResponseWithCredentialsError();
                    } else if (users.size() > 1) {
                        listener.onResponseWithMultipleMatch();
                    } else {
                        listener.onSuccessResponse(users.get(0));
                    }
                } else {
                    listener.onResponseWithCredentialsError();
                }

            } catch (Exception e) {
                listener.onResponseWithError(e.getMessage());
            }
        } else {
            listener.onResponseWithError(response.message());
        }
    }
}
