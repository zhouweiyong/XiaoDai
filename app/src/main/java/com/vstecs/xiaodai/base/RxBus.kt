package com.vstecs.xiaodai.base

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

interface RxBus<T> {

    fun post(data: T)

    fun toObservable(): Observable<T>

    companion object {
        fun <T> create(): RxBus<T> {
            return create(PublishSubject.create<T>().toSerialized())
        }

        fun <T> create(bus: Subject<T>): RxBus<T> {
            return object : RxBus<T> {
                override fun post(data: T) {
                    bus.onNext(data)
                }

                override fun toObservable(): Observable<T> {
                    return bus
                }

            }
        }
    }
}