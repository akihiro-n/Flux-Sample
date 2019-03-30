package com.example.akihiro.fluxsample

import com.example.akihiro.fluxsample.domain.entity.Item

sealed class Action {
    object ItemAction: Action() {
        class FetchNewItems(val item: List<Item>): Action()
        class FetchItemsForQuery(val item: List<Item>): Action()
        class ErrorFetchNewItems(val e: Throwable): Action()
        class ErrorFetchItemsForQuery(val e: Throwable): Action()
    }

    object ProgressAction: Action() {
        class Idle(val id: Int): Action()
        class Loading(val id: Int): Action()
    }
}