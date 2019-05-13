package com.themaker.fshmo.klassikaplus.presentation.root;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.android.material.navigation.NavigationView;
import com.themaker.fshmo.klassikaplus.App;
import com.themaker.fshmo.klassikaplus.R;
import com.themaker.fshmo.klassikaplus.data.domain.Item;
import com.themaker.fshmo.klassikaplus.data.preferences.Preferences;
import com.themaker.fshmo.klassikaplus.presentation.base.MvpAppCompatActivity;
import com.themaker.fshmo.klassikaplus.presentation.catalog.CatalogFragment;
import com.themaker.fshmo.klassikaplus.presentation.novelties.NoveltyFragment;
import com.themaker.fshmo.klassikaplus.presentation.web_item.WebItemFragment;

import javax.inject.Inject;
import java.util.List;

public class MainActivity extends MvpAppCompatActivity
        implements MainActivityView, WebItemCallback, MainNavigationCallback {

    private static final String TAG = MainActivity.class.getName();

    @Inject
    Preferences preferences;
    @InjectPresenter
    MainActivityPresenter presenter;

    private DrawerLayout drawerLayout;

    private static final String WEBVIEW_TAG = "ItemWebViewFragment";
    private static final String NOVELTY_TAG = "NoveltyFragment";
    private static final String CATALOG_TAG = "CatalogFragment";
    private static final String START_WITH_NOVELTY_EXTRA = "startwithNovelty";

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static Intent startWithNovelties(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(START_WITH_NOVELTY_EXTRA, true);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ----------------------");
        Log.i(TAG, "onCreate: STARTED");
        App.getInstance().getComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        drawerLayout = findViewById(R.id.main_base_view_group);
        NavigationView navigationView = findViewById(R.id.main_navigation);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        preferences.setFirstTimeAppLaunch();
        if (getIntent().getBooleanExtra(START_WITH_NOVELTY_EXTRA, false))
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frame, new NoveltyFragment())
                    .addToBackStack(NOVELTY_TAG)
                    .commit();
        else
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frame, new CatalogFragment())
                    .addToBackStack(CATALOG_TAG)
                    .commit();
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed: ");
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount > 1) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment fragment : fragments) {
                if (fragment instanceof WebItemFragment)
                    getSupportFragmentManager().beginTransaction()
                            .remove(fragment)
                            .commit();
                getSupportFragmentManager().popBackStack();
                Log.i(TAG, "onBackPressed: details fragment removed");
            }
        } else finish();
    }

    @Override
    public void launchItemWebViewFragment(@NonNull Item item) {
        Log.i(TAG, "launchItemWebViewFragment: displaying itemWebViewFragment with item: " + item.getName());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, WebItemFragment.newInstance(item))
                .addToBackStack(WEBVIEW_TAG)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.i(TAG, "onOptionsItemSelected: MainNavigation called"); //this is not printed out
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showMainNavigation() {
        drawerLayout = findViewById(R.id.main_base_view_group);
        drawerLayout.openDrawer(GravityCompat.START);
        Log.d(TAG, "showMainNavigation: opening navigstion");
    }

    private boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        drawerLayout.closeDrawers();
        switch (menuItem.getItemId()) {
            case R.id.nav_catalog:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frame, new CatalogFragment())
                        .addToBackStack("Catalog")
                        .commit();
                break;
            case R.id.nav_about:
                // TODO: 4/6/2019
                break;
            case R.id.nav_novelty:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frame, new NoveltyFragment())
                        .addToBackStack(NOVELTY_TAG)
                        .commit();
                break;
        }
        return true;
    }
}
