package com.example.akihiro.fluxsample

import com.example.akihiro.fluxsample.domain.entity.Item

sealed class Action {
    object ItemAction: Action() {
        class FetchNewItems(val item: List<Item>): Action()
        class FetchItemsForQuery(val item: List<Item>): Action()
    }

    object ProgressAction: Action() {
        class Idle(val id: Int): Action()
        class Loading(val id: Int): Action()
    }
}