package ar.com.wolox.android.training.ui.training.singup;

import ar.com.wolox.android.R;
import ar.com.wolox.wolmo.core.activity.WolmoActivity;

/**
 * SingUpActivity
 */
public class SingUpActivity extends WolmoActivity {
    @Override
    protected int layout() {
        return R.layout.activity_base;
    }

    @Override
    protected void init() {
        replaceFragment(R.id.vActivityBaseContent, new SingUpFragment());
    }
}
