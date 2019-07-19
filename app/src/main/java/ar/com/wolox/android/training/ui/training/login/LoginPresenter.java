package ar.com.wolox.android.training.ui.training.login;

import javax.inject.Inject;

import ar.com.wolox.android.training.utils.UserSession;
import ar.com.wolox.wolmo.core.presenter.BasePresenter;

/**
 * LoginPresenter
 */
public class LoginPresenter extends BasePresenter<ILoginView> {

    @Inject
    public LoginPresenter(UserSession userSession) {}

}
