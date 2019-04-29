package com.themaker.fshmo.klassikaplus.presentation.novelties;

import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import com.themaker.fshmo.klassikaplus.App;
import com.themaker.fshmo.klassikaplus.data.domain.Item;
import com.themaker.fshmo.klassikaplus.data.repositories.CatalogRepository;
import com.themaker.fshmo.klassikaplus.presentation.base.MvpBasePresenter;
import com.themaker.fshmo.klassikaplus.presentation.common.State;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import javax.inject.Inject;
import java.util.List;

@InjectViewState
public class NoveltyPresenter extends MvpBasePresenter<NoveltyView> {

    private static final String TAG = NoveltyPresenter.class.getName();
    // TODO: 4/26/2019 Refactor to holdData
    @Inject
    CatalogRepository repository;

    NoveltyPresenter(){
        App.getInstance().getComponent().inject(this);
    }


    void provideDataset() {
        getViewState().showState(State.Loading);
        if (repository == null)
            Log.e(TAG, "provideDataset: Repository problem");
        Disposable subscribe = repository.provideNoveltyData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::displayData,
                        this::displayError);
        getViewState().addSub(subscribe);
    }


    private void displayError(Throwable throwable) {
        logError(throwable);
        getViewState().showState(State.NoData);
    }

    private void logError(Throwable throwable) {
        Log.e(TAG, "logError: error in dataset" + throwable.getMessage());
    }

    private void displayData(List<Item> items) {
        Log.i(TAG, "provideDataset: # of items: " + items.size());
        getViewState().showState(State.HasData);
        getViewState().setDataset(items);
    }


}
