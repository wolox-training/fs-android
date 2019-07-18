package ar.com.wolox.android.training.ui.training;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ar.com.wolox.android.R;

/**
 *  Main activity
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN_ACTV";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e(TAG, "onCreate()!!");
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.main_back_msg_title))
                .setMessage(getString(R.string.main_back_msg))
                .setNegativeButton(R.string.btn_option_no, null)
                .setPositiveButton(R.string.btn_option_yes, (dialogInterface, i) -> {
                    finish();
                }).create().show();

    }
}
