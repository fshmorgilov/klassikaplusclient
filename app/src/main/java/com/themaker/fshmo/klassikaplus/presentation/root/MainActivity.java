package com.themaker.fshmo.klassikaplus.presentation.root;

import android.os.Bundle;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.themaker.fshmo.klassikaplus.R;
import com.themaker.fshmo.klassikaplus.presentation.base.MvpAppCompatActivity;
import com.themaker.fshmo.klassikaplus.presentation.novelties.NoveltyFragment;

public class MainActivity extends MvpAppCompatActivity implements MainActivityView {

    @InjectPresenter
    MainActivityPresenter presenter;

    private static final String NOVELTY_TAG = "NoveltyFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, new NoveltyFragment())
                .addToBackStack(NOVELTY_TAG)
                .commit();

    }
}
