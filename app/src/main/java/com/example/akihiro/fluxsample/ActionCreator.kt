package com.example.akihiro.fluxsample

import com.example.akihiro.fluxsample.domain.dispatcher.Dispatcher
import io.reactivex.Single

abstract class ActionCreator(private val dispatcher: Dispatcher) {

    protected fun <T, A : Action> Single<T>.dispatch(mapper: (T) -> A): Single<A> {
        return this@dispatch
            .map { type ->
                mapper(type)
            }
            .doOnSuccess { action ->
                dispatcher.dispatch(action)
            }
    }

    protected fun <T, A : Action> Single<T>.onErrorDispatch(onErrorAction: (Throwable) -> A): Single<T> {
        return this@onErrorDispatch
            .doOnError { throwable ->
                val action = onErrorAction(throwable)
                dispatcher.dispatch(action)
            }
    }
}