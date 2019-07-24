package ar.com.wolox.android.training.ui.example;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.com.wolox.android.training.ui.training.login.ILoginView;
import ar.com.wolox.android.training.ui.training.login.LoginPresenter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LoginPresenterTest{

    private ILoginView iLoginView;
    private LoginPresenter loginPresenter;

    @Before
    void createInterface() {
        iLoginView = mock(ILoginView.class);
        loginPresenter = mock(LoginPresenter.class);
        //ctx = InstrumentationRegistry.getInstrumentation().getContext();
    }

    @Test
    void uniTest(){
        CharSequence user = "test";
        CharSequence pass = "1234";
        //loginPresenter.onLoginButtonClicked(user, pass);
        String presenter = loginPresenter.toString();
        ILoginView result = verify(iLoginView, times(1));
        Assert.assertNotNull(result);
    }
}
