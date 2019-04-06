package com.themaker.fshmo.klassikaplus.presentation.novelties;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.themaker.fshmo.klassikaplus.R;
import com.themaker.fshmo.klassikaplus.data.domain.Item;
import com.themaker.fshmo.klassikaplus.presentation.base.MvpBaseFragment;
import com.themaker.fshmo.klassikaplus.presentation.common.State;
import com.themaker.fshmo.klassikaplus.presentation.decoration.GridSpaceItemDecoration;
import com.themaker.fshmo.klassikaplus.presentation.root.MainNavigationCallback;
import com.themaker.fshmo.klassikaplus.presentation.root.WebItemCallback;
import io.reactivex.disposables.Disposable;

import java.util.ArrayList;
import java.util.List;

public class NoveltyFragment extends MvpBaseFragment implements NoveltyView {

    private static final String TAG = NoveltyFragment.class.getName();
    private List<Item> dataset = new ArrayList<>();
    private RequestManager glide;
    private MainNavigationCallback mainMenuCallback;
    private WebItemCallback webItemCallback;

    @BindView(R.id.novelty_recycler) RecyclerView recycler;
    @BindView(R.id.novelty_error) TextView error;
    @BindView((R.id.novelty_toolbar)) Toolbar toolbar;

    @InjectPresenter
    NoveltyPresenter presenter;

    private NoveltyAdapter noveltyAdapter;

    @Override
    protected void onPostCreateView() {
        super.onPostCreateView();
        glide = Glide.with(rootView);
        webItemCallback = (WebItemCallback) getActivity();
        mainMenuCallback = (MainNavigationCallback) getActivity();
        setupActionBar();
        presenter.provideDataset();
        setupRecycler();
    }

    // FIXME: 2/25/2019 do not rerequest on back pressed
    private void setupRecycler() {
        noveltyAdapter = new NoveltyAdapter(
                dataset,
                glide,
                item -> {
                    Log.i(TAG, "onPostCreateView: item pressed: " + item.getId());
                    webItemCallback.launchItemWebViewFragment(item);
                }
        );
        noveltyAdapter.setDataset(dataset);
        recycler.setAdapter(noveltyAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        GridSpaceItemDecoration decoration = new GridSpaceItemDecoration(1, 1);
        recycler.addItemDecoration(decoration);
    }

    //TODO вынести в абстракнтый класс
    private void setupActionBar() {
        setHasOptionsMenu(true);
        if (toolbar == null) Log.e(TAG, "setupActionBar: toolbar is null");
        else {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.novelty_fragment;
    }

    @Override
    public void onDestroyView() {
        clearDataset();
        webItemCallback = null;
        super.onDestroyView();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void setDataset(@NonNull List<Item> newsItems) {
        if (!this.dataset.isEmpty()) clearDataset();
        noveltyAdapter.setDataset(newsItems);
        noveltyAdapter.notifyItemRangeChanged(0, newsItems.size());
    }

    @Override
    public void showError() {
    }

    @Override
    public void showState(@NonNull State state) {
        Log.i(TAG, "showState: calling state " + state.toString());
        switch (state) {
            case HasData: {
                recycler.setVisibility(View.VISIBLE);
                error.setVisibility(View.GONE);
                toolbar.setVisibility(View.VISIBLE);
                break;
            }
            case Loading: {
                recycler.setVisibility(View.GONE);
                error.setVisibility(View.GONE);
                toolbar.setVisibility(View.VISIBLE);
                break;
            }
            case NoData: {
                recycler.setVisibility(View.GONE);
                error.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.VISIBLE);
                break;
            }
        }
        // TODO: 2/7/2019 progress bar
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // FIXME: 4/6/2019 Не работает
        switch (item.getItemId()) {
            case R.id.home:
                Log.i(TAG, "onOptionsItemSelected: MainNavigation called"); //this is not printed out
                mainMenuCallback.showMainNavigation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clearDataset() {
        if (dataset != null) {
            int size = dataset.size();
            dataset.clear();
            noveltyAdapter.notifyItemRangeRemoved(0, size);
            Log.i(TAG, "clearDataset: cleared " + String.valueOf(size) + " items");
        }
    }

    @Override
    public void addSub(Disposable subscription) {
        super.addSub(subscription);
    }

}
