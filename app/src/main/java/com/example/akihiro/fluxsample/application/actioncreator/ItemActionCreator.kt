package com.example.akihiro.fluxsample.application.actioncreator

import com.example.akihiro.fluxsample.application.action.ItemAction
import com.example.akihiro.fluxsample.domain.usecase.ItemUseCase
import flux.ActionCreator

class ItemActionCreator(
    private val useCase: ItemUseCase): ActionCreator()
{

    fun fetchNewItems(page: Int) {
        useCase
            .fetchNewItems(page)
            .dispatch(ItemAction::FetchNewItems)
            .onErrorDispatch(ItemAction::ErrorFetchNewItems)
            .subscribe()
    }

    fun fetchItemsForQuery(page: Int, query: String) {
        useCase
            .fetchItemsForQuery(page, query)
            .dispatch(ItemAction::FetchItemsForQuery)
            .onErrorDispatch(ItemAction::ErrorFetchItemsForQuery)
            .subscribe()
    }

}