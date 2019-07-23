package ar.com.wolox.android.training.ui.training.login;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import ar.com.wolox.android.R;
import ar.com.wolox.android.training.model.User;
import ar.com.wolox.android.training.ui.training.main.MainActivity;
import ar.com.wolox.android.training.ui.training.singup.SingUpActivity;
import ar.com.wolox.wolmo.core.fragment.WolmoFragment;
import pl.droidsonroids.gif.GifImageView;

/**
 * LoginFragment: initial screen with animation and login settings
 */
public class LoginFragment extends WolmoFragment<LoginPresenter> implements View.OnClickListener, ILoginView {

    private static final String URL_TYC = "https://www.wolox.com.ar/";

    private View view;

    private ConstraintLayout mContentView;
    private GifImageView mLogoGif;
    private TextInputEditText mEmailTxt;
    private TextInputEditText mPassTxt;

    @Override
    public int layout() {
        return R.layout.fragment_login;
    }

    @Override
    public void init() {

        view = getView();
        if (view != null) {
            //Views
            mContentView = view.findViewById(R.id.container_view);
            mContentView.setVisibility(View.GONE);
            mLogoGif = view.findViewById(R.id.splash_animation);
            mLogoGif.setVisibility(View.VISIBLE);
            mEmailTxt = view.findViewById(R.id.user_text);
            mPassTxt = view.findViewById(R.id.pass_text);

            // Buttons
            Button mBtnLogin = view.findViewById(R.id.btn_login);
            mBtnLogin.setOnClickListener(this);
            Button mBtnSingup = view.findViewById(R.id.btn_singup);
            mBtnSingup.setOnClickListener(this);
            TextView mTvTyc = view.findViewById(R.id.tyc_txt);
            mTvTyc.setOnClickListener(this);
        }

        getPresenter().onInit();
    }

    @Override
    public void onClick(View viewOnClick) {
        // Memory optimization: instead of create one click listener from each button,
        // we created a global oneClick method for the activity
        switch (viewOnClick.getId()) {
            case R.id.btn_login:
                getPresenter().onLoginButtonClicked(mEmailTxt.getText(), mPassTxt.getText());
                break;
            case R.id.btn_singup:
                getPresenter().onSingUpButtonClicked();
                break;
            case R.id.tyc_txt:
                getPresenter().onTermsAndConditionClicked();
                break;
            default:
                break;
        }
    }

    @Override
    public void showEmptyEmailError() {
        if (view != null) {
            TextInputLayout input = view.findViewById(R.id.user_wrapper);
            input.setError(getString(R.string.error_empty_field));
            mEmailTxt.setBackgroundResource(R.drawable.back_txt_red_border);
        }
    }

    @Override
    public void showInvalidEmailError() {
        if (view != null) {
            TextInputLayout input = view.findViewById(R.id.user_wrapper);
            input.setError(getString(R.string.error_invalid_email));
            mEmailTxt.setBackgroundResource(R.drawable.back_txt_red_border);
        }
    }

    @Override
    public void showValidEmail() {
        if (view != null) {
            TextInputLayout input = view.findViewById(R.id.user_wrapper);
            input.setError(null);
            mEmailTxt.setBackgroundResource(R.drawable.back_txt_white_border);
        }
    }

    @Override
    public void showEmptyPassError() {
        if (view != null) {
            TextInputLayout input = view.findViewById(R.id.pass_wrapper);
            input.setError(getString(R.string.error_empty_field));
            mPassTxt.setBackgroundResource(R.drawable.back_txt_red_border);
        }
    }

    @Override
    public void showValidPass() {
        if (view != null) {
            TextInputLayout input = view.findViewById(R.id.pass_wrapper);
            input.setError(null);
            mPassTxt.setBackgroundResource(R.drawable.back_txt_white_border);
        }
    }

    public void showSingUpScreen() {
            mEmailTxt.setText("");
            mPassTxt.setText("");

            Intent intent = new Intent(getContext(), SingUpActivity.class);
            startActivity(intent);
    }

    @Override
    public void showTermsAndConditionWebView() {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(URL_TYC));
        startActivity(i);
    }

    @Override
    public void cleanCredentials() {
        mEmailTxt.setText("");
        mPassTxt.setText("");
    }

    @Override
    public void updateCredentials(User user) {
        mEmailTxt.setText(user.getEmail());
        mPassTxt.setText(user.getPassword());
    }

    @Override
    public void hideAnimations() {
        // Hide animation and show main screen
        mContentView.setVisibility(View.VISIBLE);
        mLogoGif.setVisibility(View.GONE);
    }

    @Override
    public void showMainScreen() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void showInvalidCredentialsError() {
        Toast.makeText(getContext(), getString(R.string.error_wrong_credentials), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMultiplesCredentialsError() {
        Toast.makeText(getContext(), getString(R.string.error_multiple_response), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showServiceError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
