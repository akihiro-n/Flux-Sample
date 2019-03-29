package com.example.akihiro.fluxsample

import com.example.akihiro.fluxsample.domain.dispatcher.Dispatcher
import com.example.akihiro.fluxsample.domain.usecase.ItemUseCase
import io.reactivex.schedulers.Schedulers

class ItemActionCreator(
    private val useCase: ItemUseCase,
    private val dispatcher: Dispatcher
)
{

    /**
     * 最新の記事一覧を取得
     * @param page
     */
    fun fetchNewItems(page: Int) {
        useCase
            .fetchNewItems(page)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {  }
            .doFinally {  }
            .subscribe({
                dispatcher.dispatch(Action.ItemAction.FetchNewItems(it))
            },{

            })
    }

    /**
     * Queryから検索した記事一覧を取得
     * @param page
     * @param query
     */
    fun fetchItemsForQuery(page: Int, query: String) {
        useCase
            .fetchItemsForQuery(page, query)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {  }
            .doFinally {  }
            .subscribe({

            },{

            })
    }
}