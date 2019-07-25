package ar.com.wolox.android.training.ui.example;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.com.wolox.android.training.model.User;
import ar.com.wolox.android.training.ui.adapters.ILoginAdapterListener;
import ar.com.wolox.android.training.ui.adapters.LoginAdapter;
import ar.com.wolox.android.training.ui.training.login.ILoginView;
import ar.com.wolox.android.training.ui.training.login.LoginPresenter;
import ar.com.wolox.android.training.utils.CredentialsSession;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LoginPresenterTest {

    private static final String EMAIL_INVALID = "testInvalidEmail.com";
    private static final String NON_EXISTENT_EMAIL = "test@test.com";
    private static final String EMAIL = "melvin.lambert15@example.com";
    private static final String PASSWORD = "qwerty";

    private static final String ERROR_SERVICE = "DefaultServiceError";

    private ILoginView iLoginView;
    private CredentialsSession credentials;

    private LoginPresenter presenter;
    private LoginAdapter adapter;

    @Before
    public void createInstances() {
        iLoginView = mock(ILoginView.class);
        credentials = mock(CredentialsSession.class);
        adapter = mock(LoginAdapter.class);

        presenter = new LoginPresenter(credentials, adapter);
    }

    @Test
    public void testInstancesCreated() {
        Assert.assertNotNull(presenter);
    }

    @Test
    public void testAlreadyUserStoredInSharedPreferences() {
        doReturn(EMAIL).when(credentials).getUsername();
        Assert.assertEquals(credentials.getUsername(), EMAIL);
    }

    @Test
    public void testDataAlreadyStoredInSharedPreferences() {
        doReturn(EMAIL).when(credentials).getUsername();
        doReturn(PASSWORD).when(credentials).getPassword();
        Assert.assertTrue(credentials.getUsername() != null && credentials.getPassword() != null);
    }

    @Test
    public void testEmptyEmailError() {
        presenter.attachView(iLoginView);
        presenter.onLoginButtonClicked("", "");
        verify(iLoginView, times(1)).showEmptyEmailError();
    }

    @Test
    public void testEmptyPassError() {
        presenter.attachView(iLoginView);
        presenter.onLoginButtonClicked("", "");
        verify(iLoginView, times(1)).showEmptyPassError();
    }

    @Test
    public void testShowInvalidEmailFormat() {
        presenter.attachView(iLoginView);
        presenter.onLoginButtonClicked(EMAIL_INVALID, PASSWORD);
        verify(iLoginView, times(1)).showInvalidEmailError();
    }

    @Test
    public void testValidEmail() {
        presenter.attachView(iLoginView);
        presenter.onLoginButtonClicked(NON_EXISTENT_EMAIL, PASSWORD);
        verify(iLoginView, times(1)).showValidEmail();
    }

    @Test
    public void testValidPassword() {
        presenter.attachView(iLoginView);
        presenter.onLoginButtonClicked(EMAIL, PASSWORD);
        verify(iLoginView, times(1)).showValidPass();
    }

    @Test
    public void testShowUnavailableNetworkError() {
        doReturn(false).when(iLoginView).isNetworkAvailable();
        presenter.attachView(iLoginView);
        presenter.onLoginButtonClicked(EMAIL, PASSWORD);
        verify(iLoginView, times(1)).showNetworkUnavailableError();
    }

    @Test
    public void testFailureResponseServiceError() {
        doReturn(true).when(iLoginView).isNetworkAvailable();
        doAnswer(e -> {
            ((ILoginAdapterListener)e.getArgument(2)).onFailure(ERROR_SERVICE);
            return null;
        }).when(adapter).getUser(any(String.class), any(String.class), any());

        presenter.attachView(iLoginView);
        presenter.onLoginButtonClicked(EMAIL, PASSWORD);
        verify(iLoginView, times(1)).showServiceError(ERROR_SERVICE);
    }

    @Test
    public void testServiceResponseWithError() {
        doReturn(true).when(iLoginView).isNetworkAvailable();
        doAnswer(e -> {
            ((ILoginAdapterListener)e.getArgument(2)).onResponseWithError(ERROR_SERVICE);
            return true;
        }).when(adapter).getUser(any(String.class), any(String.class), any());

        presenter.attachView(iLoginView);
        presenter.onLoginButtonClicked(EMAIL, PASSWORD);
        verify(iLoginView, times(1)).showServiceError(ERROR_SERVICE);
    }

    @Test
    public void testServiceResponseWithInvalidCredentials() {
        doReturn(true).when(iLoginView).isNetworkAvailable();
        doAnswer(e -> {
            ((ILoginAdapterListener)e.getArgument(2)).onResponseWithCredentialsError();
            return true;
        }).when(adapter).getUser(any(String.class), any(String.class), any());

        presenter.attachView(iLoginView);
        presenter.onLoginButtonClicked(EMAIL, PASSWORD);
        verify(iLoginView, times(1)).showInvalidCredentialsError();
    }

    @Test
    public void testServiceResponseWithMultipleMatch() {
        doReturn(true).when(iLoginView).isNetworkAvailable();
        doAnswer(e -> {
            ((ILoginAdapterListener)e.getArgument(2)).onResponseWithMultipleMatch();
            return true;
        }).when(adapter).getUser(any(String.class), any(String.class), any());

        presenter.attachView(iLoginView);
        presenter.onLoginButtonClicked(EMAIL, PASSWORD);
        verify(iLoginView, times(1)).showMultiplesCredentialsError();
    }

    @Test
    public void testServiceSuccessResponse() {
        doReturn(true).when(iLoginView).isNetworkAvailable();
        doAnswer(e -> {
            User user = new User(EMAIL, PASSWORD);
            ((ILoginAdapterListener)e.getArgument(2)).onSuccessResponse(user);
            return true;
        }).when(adapter).getUser(any(String.class), any(String.class), any());

        presenter.attachView(iLoginView);
        presenter.onLoginButtonClicked(EMAIL, PASSWORD);
        verify(iLoginView, times(1)).showMainScreen();
    }
}
