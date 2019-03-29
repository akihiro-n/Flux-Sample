package com.example.akihiro.fluxsample.domain.dispatcher

import com.example.akihiro.fluxsample.Action
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class Dispatcher {

    private val subject by lazy {
        PublishSubject.create<Action>()
    }

    val observable: Observable<Action>
        get() = subject

    fun dispatch(action: Action) {
        subject.onNext(action)
    }
}