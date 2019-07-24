package ar.com.wolox.android.training.ui.example;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.com.wolox.android.training.ui.training.login.ILoginView;
import ar.com.wolox.android.training.ui.training.login.LoginPresenter;
import ar.com.wolox.android.training.utils.CredentialsSession;
import ar.com.wolox.wolmo.networking.retrofit.RetrofitServices;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LoginPresenterTest {

    private ILoginView iLoginView;
    private CredentialsSession credentials;
    private RetrofitServices retrofitServices;

    private LoginPresenter presenter;

    @Before
    public void createInstances() {
        iLoginView = mock(ILoginView.class);
        credentials = mock(CredentialsSession.class);
        retrofitServices = mock(RetrofitServices.class);


        presenter = new LoginPresenter(credentials, retrofitServices);
    }

    @Test
    public void testInstancesCreated() {
        Assert.assertNotNull(presenter);
    }

    @Test
    public void testAlreadyUserStoredInSharedPreferences() {
        doReturn("test@test.com").when(credentials).getUsername();
        Assert.assertEquals(credentials.getUsername(), "test@test.com");
    }

    @Test
    public void testDataAlreadyStoredInSharedPreferences() {
        doReturn("test@test.com").when(credentials).getUsername();
        doReturn("1234").when(credentials).getPassword();
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
        presenter.onLoginButtonClicked("test", "1234");
        verify(iLoginView, times(1)).showInvalidEmailError();
    }

    @Test
    public void testValidEmail() {
        presenter.attachView(iLoginView);
        presenter.onLoginButtonClicked("test@hotmail.com", "1234");
        verify(iLoginView, times(1)).showValidEmail();
    }

    @Test
    public void testValidPassword() {
        presenter.attachView(iLoginView);
        presenter.onLoginButtonClicked("test@hotmail.com", "1234");
        verify(iLoginView, times(1)).showValidPass();
    }

}
