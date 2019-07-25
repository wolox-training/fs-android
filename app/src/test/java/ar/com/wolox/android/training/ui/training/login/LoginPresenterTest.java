package ar.com.wolox.android.training.ui.training.login;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.com.wolox.android.training.utils.CredentialsSession;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LoginPresenterTest {

    private static final String EMAIL_INVALID = "testInvalidEmail.com";
    private static final String NON_EXISTENT_EMAIL = "test@test.com";
    private static final String EMAIL = "melvin.lambert15@example.com";
    private static final String PASSWORD = "qwerty";

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
    public void testShowSingUpScreen() {
        presenter.attachView(iLoginView);
        presenter.onSingUpButtonClicked();
        verify(iLoginView, times(1)).showSingUpScreen();
    }

    @Test
    public void testShowTermsAndConditionsScreen() {
        presenter.attachView(iLoginView);
        presenter.onTermsAndConditionClicked();
        verify(iLoginView, times(1)).showTermsAndConditionWebView();
    }
}
