package com.example.akihiro.fluxsample

import com.example.akihiro.fluxsample.domain.dispatcher.Dispatcher
import com.example.akihiro.fluxsample.domain.usecase.ItemUseCase

class ItemActionCreator(
    private val useCase: ItemUseCase,
    dispatcher: Dispatcher): ActionCreator(dispatcher)
{

    fun fetchNewItems(page: Int) {
        useCase
            .fetchNewItems(page)
            .dispatch(Action.ItemAction::FetchNewItems)
            .onErrorDispatch(Action.ItemAction::ErrorFetchNewItems)
            .subscribe()
    }

    fun fetchItemsForQuery(page: Int, query: String) {
        useCase
            .fetchItemsForQuery(page, query)
            .dispatch(Action.ItemAction::FetchItemsForQuery)
            .onErrorDispatch(Action.ItemAction::ErrorFetchItemsForQuery)
            .subscribe()
    }

}