package ar.com.wolox.android.trainingFS.ui.training.login;

import ar.com.wolox.android.trainingFS.model.User;

/**
 * ILoginAdapterListener
 */
public interface ILoginAdapterListener {

    void onFailure(String message);
    void onError(String message);
    void onCredentialsError();
    void onMultipleMatchError();
    void onSuccess(User user);
}
