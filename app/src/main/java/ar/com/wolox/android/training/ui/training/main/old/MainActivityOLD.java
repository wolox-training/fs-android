package ar.com.wolox.android.training.ui.training.main.old;

import ar.com.wolox.android.R;
import ar.com.wolox.wolmo.core.activity.WolmoActivity;

/**
 * MainActivityOLD
 */
public class MainActivityOLD extends WolmoActivity {
    @Override
    protected int layout() {
        return R.layout.activity_base;
    }

    @Override
    protected void init() {
        replaceFragment(R.id.vActivityBaseContent, new MainFragmentOLD());
    }
}
