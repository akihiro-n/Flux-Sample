package com.example.akihiro.fluxsample.ui

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.view.View
import com.example.akihiro.fluxsample.application.actioncreator.ItemActionCreator
import com.example.akihiro.fluxsample.domain.entity.Item
import com.example.akihiro.fluxsample.domain.store.ItemStore
import flux.Store

class MainViewModel(
    private val itemActionCreator: ItemActionCreator,
    private val itemStore: ItemStore
) : ViewModel() {

    private val itemsMutableLiveData: MutableLiveData<List<Item>> by lazy {
        MutableLiveData<List<Item>>()
    }

    val items: LiveData<List<Item>>
        get() = itemsMutableLiveData

    val adapter: ItemsAdapter by lazy {
        ItemsAdapter(this@MainViewModel)
    }

    fun onCreate() {
        itemActionCreator.initialize()
        initStores()
        initActions()
    }

    @SuppressLint("CheckResult")
    private fun initStores() {
        itemStore.apply {
            itemsState
                .subscribe { item ->
                    itemsMutableLiveData.postValue(item)
                }

            errorFetchItemsState
                .filter { it !is Store.EmptyError }
                .subscribe {

                }
        }
    }

    private fun initActions() {
        fetchNewItems()
    }

    fun fetchNewItems() {
        itemActionCreator.fetchNewItems(itemStore.nextPageState)
    }

}