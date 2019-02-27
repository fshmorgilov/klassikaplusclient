package com.themaker.fshmo.klassikaplus.presentation.novelties;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.BindViews;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.themaker.fshmo.klassikaplus.R;
import com.themaker.fshmo.klassikaplus.data.domain.Item;
import com.themaker.fshmo.klassikaplus.presentation.base.MvpBaseFragment;
import com.themaker.fshmo.klassikaplus.presentation.common.State;
import com.themaker.fshmo.klassikaplus.presentation.decoration.GridSpaceItemDecoration;
import com.themaker.fshmo.klassikaplus.presentation.root.MainActivityCallback;
import io.reactivex.disposables.Disposable;

import java.util.ArrayList;
import java.util.List;

public class NoveltyFragment extends MvpBaseFragment implements NoveltyView {

    private static final String TAG = NoveltyFragment.class.getName();
    private List<Item> dataset = new ArrayList<>();
    private RequestManager glide;

    @BindView(R.id.novelty_recycler) RecyclerView recycler;
    @BindView(R.id.novelty_error) TextView error;

    @InjectPresenter
    NoveltyPresenter presenter;
    MainActivityCallback callback;
    ItemTouchHelper itemTouchHelper;

    private NoveltyAdapter noveltyAdapter;

    @Override
    protected void onPostCreateView() {
        super.onPostCreateView();
        glide = Glide.with(rootView);
        callback = (MainActivityCallback) getActivity();
        presenter.provideDataset();

        // FIXME: 2/25/2019 do not rerequest on back pressed

        noveltyAdapter = new NoveltyAdapter(
                dataset,
                glide,
                item -> {
                    Log.i(TAG, "onPostCreateView: item pressed: " + item.getId());
                    callback.launchItemWebViewFragment(item);
                }
        );
        noveltyAdapter.setDataset(dataset);
        recycler.setAdapter(noveltyAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        GridSpaceItemDecoration decoration = new GridSpaceItemDecoration(1, 1);
        recycler.addItemDecoration(decoration);
        NoveltySwipeController controller = new NoveltySwipeController(new NoveltySwipeControllerActions() {
            @Override
            public void onLeftClicked(int viewHolderPosition) {
                showToast("left clicked");
            }

            @Override
            public void onRightClicked(int viewHolderPosition) {
                showToast("right clicked");

            }
        });
        itemTouchHelper = new ItemTouchHelper(controller);
        itemTouchHelper.attachToRecyclerView(recycler);

    }

    @Override
    protected int setLayoutRes() {
        return R.layout.novelty_fragment;
    }

    @Override
    public void onDestroyView() {
        clearDataset();
        callback = null;
        super.onDestroyView();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void setDataset(@NonNull List<Item> newsItems) {
        if (!this.dataset.isEmpty()) {
            clearDataset();
        }
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
                break;
            }
            case Loading: {
                recycler.setVisibility(View.GONE);
                error.setVisibility(View.GONE);
                break;
            }
            case NoData: {
                recycler.setVisibility(View.GONE);
                error.setVisibility(View.VISIBLE);
                break;
            }
        }
        // TODO: 2/7/2019 progress bar
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
