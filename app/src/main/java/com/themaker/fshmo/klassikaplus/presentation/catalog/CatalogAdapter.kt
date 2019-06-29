package com.themaker.fshmo.klassikaplus.presentation.catalog

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.themaker.fshmo.klassikaplus.R
import com.themaker.fshmo.klassikaplus.data.domain.Item

class CatalogAdapter(glide: RequestManager, items: MutableList<Item>, clickListener : (Item) -> Unit) :
    RecyclerView.Adapter<CatalogViewHolder>() {
    private val dataset: MutableList<Item> = items
    private val onItemClickListener = clickListener
    private val glide: RequestManager = glide

    private val TAG = CatalogAdapter::class.java.name

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.novelties, parent, false) as View
        return CatalogViewHolder(v)
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        holder.bind(dataset[position], glide, onItemClickListener)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun addItem(item: Item) {
        this.dataset.add(item)
        notifyItemInserted(dataset.indexOf(item))
    }

    fun setDataset(newsItems: List<Item>) {
        val currSize = this.dataset.size
        this.dataset.addAll(newsItems)
        notifyItemRangeInserted(currSize + 1, newsItems.size)
        Log.i(TAG, "Dataset changed. Added: " + newsItems.size + " items")
    }
}