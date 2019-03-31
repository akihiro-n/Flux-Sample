package com.example.akihiro.fluxsample.domain.store

import android.annotation.SuppressLint
import com.example.akihiro.fluxsample.application.action.ItemAction
import com.example.akihiro.fluxsample.domain.entity.Item
import flux.Store
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class ItemStore : Store() {

    private val itemsSubject = BehaviorSubject.create<List<Item>>()

    private val fetchItemErrorSubject = BehaviorSubject.create<Throwable>()

    val itemsState: Observable<List<Item>>
        get() = itemsSubject

    val errorFetchItemsState: Observable<Throwable>
        get() = fetchItemErrorSubject

    init {
        actionObservable()
    }

    @SuppressLint("CheckResult")
    private fun actionObservable() {
        observable
            .ofType(ItemAction.FetchNewItems::class.java)
            .map(ItemAction.FetchNewItems::item)
            .subscribe(itemsSubject::onNext)

        observable
            .ofType(ItemAction.FetchItemsForQuery::class.java)
            .map(ItemAction.FetchItemsForQuery::item)
            .subscribe(itemsSubject::onNext)

        observable
            .ofType(ItemAction.ErrorFetchNewItems::class.java)
            .map(ItemAction.ErrorFetchNewItems::throwable)
            .subscribe(fetchItemErrorSubject::onNext)

        observable
            .ofType(ItemAction.ErrorFetchItemsForQuery::class.java)
            .map(ItemAction.ErrorFetchItemsForQuery::throwable)
            .subscribe(fetchItemErrorSubject::onNext)
    }
}