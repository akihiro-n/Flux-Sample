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
    private var itemsSubject = BehaviorSubject.create<List<Item>>()
    private var fetchItemErrorSubject = BehaviorSubject.create<Throwable>()

    fun nextPageState(): Int = nextPage
    fun itemsState(): Observable<List<Item>> = itemsSubject
    fun errorFetchItemsState(): Observable<Throwable> = fetchItemErrorSubject

    init {
        initActionObservables()
    }

    @SuppressLint("CheckResult")
    private fun initActionObservables() {
        observable
            .updateNextPageState()
            .ofType(ItemAction.Initialize::class.java)
            .subscribe {
                nextPage = 1
                itemsSubject = BehaviorSubject.create<List<Item>>()
                fetchItemErrorSubject = BehaviorSubject.create<Throwable>()
            }

        observable
            .updateNextPageState()
            .ofType(ItemAction.FetchNewItems::class.java)
            .map(ItemAction.FetchNewItems::item)
            .subscribe(itemsSubject::onNext)

        observable
            .updateNextPageState()
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

    private fun Observable<Action>.updateNextPageState() = doOnNext { nextPage += 1 }

}