package ar.com.wolox.android.training.ui.training.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import ar.com.wolox.android.R;
import ar.com.wolox.wolmo.core.fragment.WolmoFragment;
import pl.droidsonroids.gif.GifImageView;

/**
 * LoginFragment
 */
public class LoginFragment extends WolmoFragment<LoginPresenter> implements View.OnClickListener {

    private final String tag = "LOGIN_FRGM";
    private final long delay = 5000L;
    private final String urlTyc = "https://www.wolox.com.ar/";

    private View view;

    private RelativeLayout mContentView;
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
            mContentView = view.findViewById(R.id.container_view);
            mContentView.setVisibility(View.GONE);
            mLogoGif = view.findViewById(R.id.splash_animation);
            mLogoGif.setVisibility(View.VISIBLE);
            mEmailTxt = view.findViewById(R.id.user_text);
            mPassTxt = view.findViewById(R.id.pass_text);

            Button mBtnLogin = view.findViewById(R.id.btn_login);
            mBtnLogin.setOnClickListener(this);
            Button mBtnSingup = view.findViewById(R.id.btn_singup);
            mBtnSingup.setOnClickListener(this);
            TextView mTvTyc = view.findViewById(R.id.tyc_txt);
            mTvTyc.setOnClickListener(this);
        }

        new Handler().postDelayed(this::initMainScreen, delay);
    }

    private void initMainScreen() {

        mContentView.setVisibility(View.VISIBLE);
        mLogoGif.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View viewOnClick) {
        switch (viewOnClick.getId()) {
            case R.id.btn_login:
                toMain();
                break;
            case R.id.btn_singup:
                toSingUp();
                break;
            case R.id.tyc_txt:
                toTYCdetails();
                break;
            default:
                break;
        }
    }

    private void toMain() {
        Log.e(tag, "toMain() *****************");

        if (validateForm()) {
            Log.e(tag, "isValid!!!");
        }
    }

    private void toSingUp() {
        Log.e(tag, "toSingUp() *****************");
    }

    private void toTYCdetails() {
        Log.e(tag, "toTYCdetails() *****************");

        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(urlTyc));
        startActivity(i);
    }

    private boolean isValidEmail(CharSequence target) {

        if (view != null) {

            TextInputLayout input = view.findViewById(R.id.user_wrapper);
            if (TextUtils.isEmpty(target)) {
                //input.setEnabled(true);
                input.setError(getString(R.string.error_empty_field));
                return false;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(target).matches()) {
                //input.setEnabled(true);
                input.setError(getString(R.string.error_invalid_email));
                return false;
            } else {
                //input.setEnabled(false);
                input.setError(null);
                return true;
            }
        } else return false;
    }

    private boolean isValidPass(CharSequence target) {

        if (view != null) {

            TextInputLayout input = view.findViewById(R.id.pass_wrapper);
            if (TextUtils.isEmpty(target)) {
                //input.setEnabled(true);
                input.setError(getString(R.string.error_empty_field));
                return false;
            } else {
                //input.setEnabled(false);
                input.setError(null);
                return true;
            }
        } else return false;

    }

    private boolean validateForm() {
        boolean isValid;

        isValid = isValidEmail(mEmailTxt.getText());
        isValid = isValid && isValidPass(mPassTxt.getText());

        return isValid;
    }
}
