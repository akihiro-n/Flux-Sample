package com.example.akihiro.fluxsample.application.action

import com.example.akihiro.fluxsample.domain.entity.Item
import flux.Action
import flux.ErrorAction

sealed class ItemAction: Action {
    object Initialize: ItemAction()
    class FetchNewItems(val item: List<Item>): ItemAction()
    class FetchItemsForQuery(val item: List<Item>): ItemAction()
    class ErrorFetchNewItems(override val throwable: Throwable): ErrorAction
    class ErrorFetchItemsForQuery(override val throwable: Throwable): ErrorAction
}