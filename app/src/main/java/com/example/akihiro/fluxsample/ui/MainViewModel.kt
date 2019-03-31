package com.example.akihiro.fluxsample.ui

import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.akihiro.fluxsample.application.actioncreator.ItemActionCreator
import com.example.akihiro.fluxsample.domain.store.ItemStore

class MainViewModel(
    private val actionCreator: ItemActionCreator,
    private val itemStore: ItemStore
) : ViewModel() {

    fun getItems() {
        actionCreator.fetchNewItems(1)
        itemStore.itemsState.subscribe {
            Log.v("あああ", "${it}")
        }
    }

}