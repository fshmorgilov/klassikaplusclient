package com.themaker.fshmo.klassikaplus.presentation.catalog

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.themaker.fshmo.klassikaplus.R
import com.themaker.fshmo.klassikaplus.data.domain.Item
import com.themaker.fshmo.klassikaplus.data.domain.ItemCategory
import com.themaker.fshmo.klassikaplus.presentation.base.MvpBaseFragment
import com.themaker.fshmo.klassikaplus.presentation.common.State
import com.themaker.fshmo.klassikaplus.presentation.decoration.GridSpaceItemDecoration
import com.themaker.fshmo.klassikaplus.presentation.root.MainNavigationCallback
import com.themaker.fshmo.klassikaplus.presentation.root.WebItemCallback
import io.reactivex.disposables.Disposable

class CatalogFragment : MvpBaseFragment(), CatalogView {

    private val TAG = javaClass.name
    private val dataset = ArrayList<Item>()

    private lateinit var error: LinearLayout
    private lateinit var errorRetryBtn: TextView
    private lateinit var toolbar: Toolbar
    private lateinit var recycler: RecyclerView

    private lateinit var webItemCallback: WebItemCallback
    private lateinit var navigationCallback: MainNavigationCallback
    private lateinit var recyclerAdapter: CatalogAdapter

    private var categories: List<ItemCategory> = ArrayList()
    private var currentCategoryId: Int = 2

    @InjectPresenter
    internal lateinit var presenter: CatalogPresenter

    private lateinit var glide: RequestManager

    override fun onBackPressed() {
        activity?.onBackPressed()
    }

    override fun onPostCreateView() {
        super.onPostCreateView()
        webItemCallback = activity as WebItemCallback
        navigationCallback = activity as MainNavigationCallback
        initializeView()
        errorRetryBtn.setOnClickListener { presenter.provideDataset(currentCategoryId) }
        glide = Glide.with(this)
        presenter.provideDataset(currentCategoryId)
        presenter.provideCategories()
        setupActionBar()
    }

    private fun initializeView() {
        toolbar = rootView.findViewById(R.id.catalog_toolbar)
        recycler = rootView.findViewById(R.id.catalog_recycler)
        error = rootView.findViewById(R.id.catalog_error_layout)
        errorRetryBtn = rootView.findViewById(R.id.error_refresh_btn)
    }

    private fun setupActionBar() {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        val actionBar: ActionBar? = (activity as AppCompatActivity).supportActionBar
        toolbar.title = rootView.resources.getString(R.string.catalog)
        with(actionBar) {
            this?.setDisplayHomeAsUpEnabled(true)
            this?.setHomeAsUpIndicator(R.drawable.ic_menu)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(recycler) {
            val catalogAdapter = CatalogAdapter(glide, dataset, webItemCallback::launchItemWebViewFragment)
            layoutManager = LinearLayoutManager(activity)
            catalogAdapter.setDataset(dataset)
            adapter = catalogAdapter
            recyclerAdapter = catalogAdapter
            addItemDecoration(GridSpaceItemDecoration(1, 1))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.catalog_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navigationCallback.showMainNavigation()
                return true
            }
            R.id.category_selection -> {
                //TODO перенести во фрагмент
                val categoriesNames = categories.map { it.name }
                var selectedCategory: Int? = currentCategoryId
                context?.let {
                    val dialog = AlertDialog.Builder(it)
                    dialog.setTitle(getString(R.string.choose_category))
                        .setItems(categoriesNames.toTypedArray()) { _: DialogInterface?, which: Int ->
                            selectedCategory =
                                categories.find { category -> category.name == categoriesNames[which] }?.id
                            Log.i(TAG, "Selected category: $selectedCategory")
                            currentCategoryId = selectedCategory!!
                            presenter.provideDataset(currentCategoryId)
                        }
                        .setNegativeButton(R.string.cancel) { dialog, which -> dialog.dismiss() }
                        .create()
                        .show()
                }
                return true
            }
            else -> {
                super.onOptionsItemSelected(item); return false
            }
        }
    }

    override fun setDataset(items: List<Item>) {
        dataset.apply {
            if (isEmpty()) {
                addAll(items)
                recyclerAdapter.notifyItemRangeInserted(0, size)
            } else {
                val sizeHolder = size
                clear()
                recyclerAdapter.notifyItemRangeRemoved(0, sizeHolder)
                addAll(items)
                recyclerAdapter.notifyItemRangeInserted(0, items.size)
            }
            Log.i(TAG, "Setting dataset")
        }
    }

    override fun showError() {
//
    }

    override fun showState(state: State) {
        when (state) {
            State.HasData -> {
                recycler.visibility = View.VISIBLE
                toolbar.visibility = View.VISIBLE
                error.visibility = View.GONE
                errorRetryBtn.visibility = View.GONE
            }
            State.Loading -> {
                recycler.visibility = View.GONE
                toolbar.visibility = View.VISIBLE
                error.visibility = View.GONE
                errorRetryBtn.visibility = View.GONE
            }
            State.NetworkError -> {
                recycler.visibility = View.GONE
                toolbar.visibility = View.VISIBLE
                error.visibility = View.VISIBLE
                errorRetryBtn.visibility = View.VISIBLE
            }
            else -> throw IllegalStateException()
        }
    }

    override fun setCategories(categories: List<ItemCategory>) {
        this.categories = categories
    }

    override fun addSub(subscription: Disposable) {
        super.addSub(subscription)
    }

    override fun setLayoutRes(): Int {
        return R.layout.catalog_fragment
    }
}
