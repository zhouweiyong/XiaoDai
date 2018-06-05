package com.vstecs.xiaodai.base

import io.reactivex.*

fun Completable.end() {
    subscribe(RxSafeObserver<Any>())
}

fun <T> Single<T>.end() {
    subscribe(RxSafeObserver())
}

fun <T> Maybe<T>.end() {
    subscribe(RxSafeObserver())
}

fun <T> Observable<T>.end() {
    subscribe(RxSafeObserver())
}

fun <T> Flowable<T>.end() {
    subscribe(RxSafeObserver())
}