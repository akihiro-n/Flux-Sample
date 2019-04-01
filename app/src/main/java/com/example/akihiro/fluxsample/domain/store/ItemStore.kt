package com.example.akihiro.fluxsample.domain.store

import android.annotation.SuppressLint
import com.example.akihiro.fluxsample.application.action.ItemAction
import com.example.akihiro.fluxsample.domain.entity.Item
import flux.Action
import flux.Store
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class ItemStore : Store() {

    private var nextPage = 1

    private val itemsSubject = BehaviorSubject.create<List<Item>>()

    private val fetchItemErrorSubject = BehaviorSubject.create<Throwable>()

    val nextPageState: () -> Int = {
        nextPage
    }

    val itemsState: Observable<List<Item>>
        get() = itemsSubject

    val errorFetchItemsState: Observable<Throwable>
        get() = fetchItemErrorSubject

    init {
        initActionObservables()
    }

    @SuppressLint("CheckResult")
    private fun initActionObservables() {
        observable
            .addNextPageState()
            .ofType(ItemAction.FetchNewItems::class.java)
            .map(ItemAction.FetchNewItems::item)
            .subscribe(itemsSubject::onNext)

        observable
            .addNextPageState()
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

    private fun Observable<Action>.addNextPageState(): Observable<Action> = this@addNextPageState
        .doOnNext {
            nextPage += 1
        }

}