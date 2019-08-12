package ar.com.wolox.android.trainingFS.ui.example

import ar.com.wolox.android.trainingFS.utils.UserSession

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class ExamplePresenterTest {

    private lateinit var mExampleView: IExampleView
    private lateinit var mExamplePresenter: ExamplePresenter
    private lateinit var mUserSession: UserSession

    @Before
    fun createInstances() {
        mExampleView = mock(IExampleView::class.java)
        mUserSession = mock(UserSession::class.java)
        mExamplePresenter = ExamplePresenter(mUserSession)
    }

    @Test
    fun usernameIsStored() {
        mExamplePresenter.attachView(mExampleView)
        mExamplePresenter.storeUsername("Test")
        Mockito.verify<UserSession>(mUserSession, Mockito.times(1)).username = "Test"
    }

    @Test
    fun storeUsernameUpdatesView() {
        mExamplePresenter.attachView(mExampleView)
        mExamplePresenter.storeUsername("Test")
        Mockito.verify<IExampleView>(mExampleView, Mockito.times(1)).onUsernameSaved()
    }
}