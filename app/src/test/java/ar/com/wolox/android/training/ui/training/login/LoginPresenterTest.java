package ar.com.wolox.android.training.ui.training.login;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.com.wolox.android.training.model.User;
import ar.com.wolox.android.training.utils.CredentialsSession;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LoginPresenterTest {

    private static final String ERROR_SERVICE = "DefaultServiceError";

    private static final String EMAIL_INVALID = "testInvalidEmail.com";
    private static final String NON_EXISTENT_EMAIL = "test@test.com";
    private static final String EMAIL = "melvin.lambert15@example.com";
    private static final String PASSWORD = "qwerty";

    private ILoginView mILoginView;
    private CredentialsSession mCredentials;
    private LoginAdapter mAdapter;

    private LoginPresenter mPresenter;

    @Before
    public void createInstances() {
        mILoginView = mock(ILoginView.class);
        mCredentials = mock(CredentialsSession.class);
        mAdapter = mock(LoginAdapter.class);

        mPresenter = new LoginPresenter(mCredentials, mAdapter);
        mPresenter.attachView(mILoginView);
    }

    @Test
    public void testCredentialsStoredInSharedPreferences() {
        doReturn(true).when(mILoginView).isNetworkAvailable();
        doAnswer(e -> {
            User user = new User(EMAIL, PASSWORD);
            ((ILoginAdapterListener)e.getArgument(2)).onSuccess(user);
            return true;
        }).when(mAdapter).getUser(any(String.class), any(String.class), any());

        mPresenter.onLoginButtonClicked(EMAIL, PASSWORD);
        verify(mCredentials, times(1)).setUsername(EMAIL);
        verify(mCredentials, times(1)).setPassword(PASSWORD);
    }

    @Test
    public void testEmptyCredentialsError() {
        mPresenter.onLoginButtonClicked("", "");
        verify(mILoginView, times(1)).showEmptyEmailError();
        verify(mILoginView, times(1)).showEmptyPassError();
    }

    @Test
    public void testShowInvalidEmailFormat() {
        mPresenter.onLoginButtonClicked(EMAIL_INVALID, PASSWORD);
        verify(mILoginView, times(1)).showInvalidEmailError();
    }

    @Test
    public void testValidEmail() {
        mPresenter.onLoginButtonClicked(NON_EXISTENT_EMAIL, PASSWORD);
        verify(mILoginView, times(1)).showValidEmail();
    }

    @Test
    public void testValidPassword() {
        mPresenter.onLoginButtonClicked(EMAIL, PASSWORD);
        verify(mILoginView, times(1)).showValidPass();
    }

    @Test
    public void testShowUnavailableNetworkError() {
        doReturn(false).when(mILoginView).isNetworkAvailable();
        mPresenter.onLoginButtonClicked(EMAIL, PASSWORD);
        verify(mILoginView, times(1)).showNetworkUnavailableError();
    }

    @Test
    public void testShowSingUpScreen() {
        mPresenter.onSingUpButtonClicked();
        verify(mILoginView, times(1)).showSingUpScreen();
    }

    @Test
    public void testShowTermsAndConditionsScreen() {
        mPresenter.onTermsAndConditionClicked();
        verify(mILoginView, times(1)).showTermsAndConditionWebView();
    }

    @Test
    public void testFailureResponseServiceError() {
        doReturn(true).when(mILoginView).isNetworkAvailable();
        doAnswer(e -> {
            ((ILoginAdapterListener)e.getArgument(2)).onFailure(ERROR_SERVICE);
            return null;
        }).when(mAdapter).getUser(any(String.class), any(String.class), any());

        mPresenter.onLoginButtonClicked(EMAIL, PASSWORD);
        verify(mILoginView, times(1)).showServiceError(ERROR_SERVICE);
    }

    @Test
    public void testServiceResponseWithError() {
        doReturn(true).when(mILoginView).isNetworkAvailable();
        doAnswer(e -> {
            ((ILoginAdapterListener)e.getArgument(2)).onError(ERROR_SERVICE);
            return true;
        }).when(mAdapter).getUser(any(String.class), any(String.class), any());

        mPresenter.onLoginButtonClicked(EMAIL, PASSWORD);
        verify(mILoginView, times(1)).showServiceError(ERROR_SERVICE);
    }

    @Test
    public void testServiceResponseWithInvalidCredentials() {
        doReturn(true).when(mILoginView).isNetworkAvailable();
        doAnswer(e -> {
            ((ILoginAdapterListener)e.getArgument(2)).onCredentialsError();
            return true;
        }).when(mAdapter).getUser(any(String.class), any(String.class), any());

        mPresenter.onLoginButtonClicked(EMAIL, PASSWORD);
        verify(mILoginView, times(1)).showInvalidCredentialsError();
    }

    @Test
    public void testServiceResponseWithMultipleMatch() {
        doReturn(true).when(mILoginView).isNetworkAvailable();
        doAnswer(e -> {
            ((ILoginAdapterListener)e.getArgument(2)).onMultipleMatchError();
            return true;
        }).when(mAdapter).getUser(any(String.class), any(String.class), any());

        mPresenter.onLoginButtonClicked(EMAIL, PASSWORD);
        verify(mILoginView, times(1)).showMultiplesCredentialsError();
    }

    @Test
    public void testServiceSuccessResponse() {
        doReturn(true).when(mILoginView).isNetworkAvailable();
        doAnswer(e -> {
            User user = new User(EMAIL, PASSWORD);
            ((ILoginAdapterListener)e.getArgument(2)).onSuccess(user);
            return true;
        }).when(mAdapter).getUser(any(String.class), any(String.class), any());

        mPresenter.onLoginButtonClicked(EMAIL, PASSWORD);
        verify(mILoginView, times(1)).showMainScreen();
    }
}
