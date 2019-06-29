package com.themaker.fshmo.klassikaplus.presentation.catalog

import androidx.annotation.NonNull
import com.arellomobile.mvp.MvpView
import com.themaker.fshmo.klassikaplus.data.domain.Item
import com.themaker.fshmo.klassikaplus.data.domain.ItemCategory
import com.themaker.fshmo.klassikaplus.presentation.common.State
import io.reactivex.disposables.Disposable

interface CatalogView : MvpView {

    fun setDataset(items: List<Item>)

    fun showError()

    fun showState(@NonNull state: State)

    fun addSub(subscription: Disposable)

    fun setCategories(categories:List<ItemCategory>)
}