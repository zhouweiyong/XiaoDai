package com.vstecs.xiaodai.base

import com.vstecs.xiaodai.App
import io.reactivex.*
import io.reactivex.disposables.Disposable
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class RxSafeObserver<T> : CompletableObserver, SingleObserver<T>, MaybeObserver<T>, Observer<T>, Subscriber<T> {
    override fun onError(e: Throwable) {
        App.debug { e.printStackTrace() }
    }

    override fun onNext(t: T) {
    }

    override fun onComplete() {
    }

    override fun onSubscribe(s: Subscription) {
        s.request(Long.MAX_VALUE)
    }

    override fun onSubscribe(d: Disposable) {
    }

    override fun onSuccess(t: T) {
        onNext(t)
    }
}
