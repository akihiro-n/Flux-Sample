package com.example.akihiro.fluxsample.ui

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.akihiro.fluxsample.databinding.CellItemListBinding
import com.example.akihiro.fluxsample.R

class ItemsAdapter(private val viewModel: MainViewModel) : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder = ViewHolder(p0)

    override fun getItemCount(): Int {
        val value = viewModel.items.value ?: return 0
        return value.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        viewModel.items.value?.let { item ->
            p0.binding.item = item[p1]
        }
    }

    inner class ViewHolder(
        viewGroup: ViewGroup,
        val binding: CellItemListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.cell_item_list,
            viewGroup,
            false
        )
    ) : RecyclerView.ViewHolder(binding.root)
}