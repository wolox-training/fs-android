package ar.com.wolox.android.training.ui.training;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import ar.com.wolox.android.R;

/**
 *   Login activity
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LOGIN_ACTV";
    private static final String URL_TYC = "https://github.com/wolox-training/fs-android";

    private Context ctx;
    private TextInputEditText mEmailTxt;
    private TextInputEditText mPassTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ctx = this;
        Button mBtnLogin = findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
        Button mBtnSingup = findViewById(R.id.btn_singup);
        mBtnSingup.setOnClickListener(this);
        TextView mTvTyc = findViewById(R.id.tyc_txt);
        mTvTyc.setOnClickListener(this);

        mEmailTxt = findViewById(R.id.user_text);
        mPassTxt = findViewById(R.id.pass_text);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
        Log.e(TAG, "toMain() *****************");

        if (validateForm()) {
            Log.e(TAG, "isValid!!!");
        } else {
            Log.e(TAG, "errorInForm!!!!");
        }
    }

    private void toSingUp() {
        Log.e(TAG, "toSingUp() *****************");
    }

    private void toTYCdetails() {
        Log.e(TAG, "toTYCdetails() *****************");

        try {
            Intent i = new Intent("android.intent.action.MAIN");
            i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
            i.addCategory("android.intent.category.LAUNCHER");
            i.setData(Uri.parse(URL_TYC));
            startActivity(i);
        } catch (ActivityNotFoundException e) {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(URL_TYC));
            startActivity(i);
        }
    }

    private boolean isValidEmail(CharSequence target) {

        TextInputLayout input = findViewById(R.id.user_wrapper);
        if (TextUtils.isEmpty(target)) {
            input.setEnabled(true);
            input.setError(getString(R.string.error_empty_field));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(target).matches()) {
            input.setEnabled(true);
            input.setError(getString(R.string.error_invalid_email));
            return false;
        } else {
            input.setEnabled(false);
            return true;
        }
    }

    private boolean isValidPass(CharSequence target) {
        //return !TextUtils.isEmpty(target);

        TextInputLayout input = findViewById(R.id.pass_wrapper);
        if (TextUtils.isEmpty(target)) {
            input.setEnabled(true);
            input.setError(getString(R.string.error_empty_field));
            return false;
        } else {
            input.setEnabled(false);
            return true;
        }
    }

    private boolean validateForm() {
        boolean isValid;

        isValid = isValidEmail(mEmailTxt.getText());
        isValid = isValid && isValidPass(mPassTxt.getText());

        return isValid;
    }
}
