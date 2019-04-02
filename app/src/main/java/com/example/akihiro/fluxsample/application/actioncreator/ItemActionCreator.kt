package com.example.akihiro.fluxsample.application.actioncreator

import com.example.akihiro.fluxsample.application.action.ItemAction
import com.example.akihiro.fluxsample.domain.usecase.ItemUseCase
import flux.ActionCreator

class ItemActionCreator(
    private val useCase: ItemUseCase): ActionCreator()
{

    fun initialize() {
        dispatch(ItemAction.Initialize)
    }

    fun fetchNewItems(page: Int) {
        useCase
            .fetchNewItems(page)
            .onSuccessDispatch(ItemAction::FetchNewItems)
            .onErrorDispatch(ItemAction::ErrorFetchNewItems)
            .subscribe()
    }

    fun fetchItemsForQuery(page: Int, query: String) {
        useCase
            .fetchItemsForQuery(page, query)
            .onSuccessDispatch(ItemAction::FetchItemsForQuery)
            .onErrorDispatch(ItemAction::ErrorFetchItemsForQuery)
            .subscribe()
    }

}