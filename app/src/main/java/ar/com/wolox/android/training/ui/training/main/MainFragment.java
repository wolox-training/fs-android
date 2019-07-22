package ar.com.wolox.android.training.ui.training.main;

import ar.com.wolox.android.R;
import ar.com.wolox.wolmo.core.fragment.WolmoFragment;

/**
 * MainFragment
 */
public class MainFragment extends WolmoFragment<MainPresenter> implements IMainView {
    @Override
    public int layout() {
        return R.layout.fragment_main;
    }

    @Override
    public void init() {

    }
}
