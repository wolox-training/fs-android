package ar.com.wolox.android.training.ui.training;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ar.com.wolox.android.R;

/**
 *  SingUp activity
 */
public class SingUpActivity extends AppCompatActivity {

    private static final String TAG = "SINGUP_ACTV";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.e(TAG, "onCreate()!!");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SingUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}