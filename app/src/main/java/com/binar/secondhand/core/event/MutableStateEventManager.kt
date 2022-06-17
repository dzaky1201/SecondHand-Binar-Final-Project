package com.binar.secondhand.core.event

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class MutableStateEventManager<T : Any> : StateEventManager<T>() {
    private val disposables = CompositeDisposable()
    override val stateEventSubject: PublishSubject<StateEvent<T>> = PublishSubject.create()

    override var value: StateEvent<T> = StateEvent.Loading()
    override var onLoading: () -> Unit = {}
    override var onSuccess: (data: T) -> Unit = {}
    override var onFailure: (code: Int, exception: Throwable) -> Unit = { _, _ -> }

    init {
        val disposable = stateEventSubject
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { stateEvent ->
                value = stateEvent
                when (stateEvent) {
                    is StateEvent.Loading -> onLoading.invoke()
                    is StateEvent.Success -> onSuccess.invoke(stateEvent.data)
                    is StateEvent.Failure -> onFailure.invoke(stateEvent.code, stateEvent.exception)
                }
            }
        disposables.add(disposable)
    }

    fun post(stateEvent: StateEvent<T>) {
        stateEventSubject.onNext(stateEvent)
    }

    override fun close() {
        disposables.dispose()
    }
}