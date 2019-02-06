package com.themaker.fshmo.klassikaplus.presentation.novelties;

import com.arellomobile.mvp.MvpView;
import com.themaker.fshmo.klassikaplus.data.domain.Item;

import java.util.List;

public interface NoveltyView extends MvpView {

    void setDataset(List<Item> items);

    void showError();
}
