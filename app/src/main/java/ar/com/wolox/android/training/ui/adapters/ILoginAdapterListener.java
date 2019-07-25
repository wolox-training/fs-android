package ar.com.wolox.android.training.ui.adapters;

import ar.com.wolox.android.training.model.User;

/**
 * ILoginAdapterListener
 */
public interface ILoginAdapterListener {

    void onResponseWithError(String message);
    void onResponseWithCredentialsError();
    void onResponseWithMultipleMatch();
    void onSuccessResponse(User user);
}
