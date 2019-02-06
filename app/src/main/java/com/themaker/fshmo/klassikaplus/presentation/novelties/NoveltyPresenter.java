package com.themaker.fshmo.klassikaplus.presentation.novelties;

import android.annotation.SuppressLint;
import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import com.themaker.fshmo.klassikaplus.data.repositories.CatalogRepository;
import com.themaker.fshmo.klassikaplus.presentation.base.MvpBasePresenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class NoveltyPresenter extends MvpBasePresenter<NoveltyView> {

    private static final String TAG = NoveltyPresenter.class.getName();

    //    @Inject
    CatalogRepository repository = CatalogRepository.getInstance();
// TODO: 2/6/2019 Подумать, как мочить disposable при onDestroy

    @SuppressLint("CheckResult")
    void provideDataset() {
        if (repository == null)
            Log.e(TAG, "provideDataset: Repository problem");
        Disposable subscribe = repository.provideNoveltyData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        items -> {
                            Log.i(TAG, "provideDataset: # of items: " + items.size());
                                getViewState().setDataset(items);
                        },
                        this::displayError);
        // TODO: 2/6/2019 Show state loading loaded etc
    }

    private void displayError(Throwable throwable) {
        logError(throwable);
        getViewState().showError();
    }

    private void logError(Throwable throwable) {
        Log.e(TAG, "logError: error in dataset" + throwable.getMessage());
    }

}
