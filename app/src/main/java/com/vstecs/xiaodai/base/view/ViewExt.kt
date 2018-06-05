package com.vstecs.xiaodai.base.view

import android.app.Activity
import android.content.ContextWrapper
import android.support.annotation.CheckResult
import android.view.View
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.RxLifecycleAndroid

val View.activity: Activity?
    get() {
        var ctx = context
        while (ctx is ContextWrapper) {
            if (ctx is Activity) return ctx
            ctx = ctx.baseContext
        }
        return null
    }

@CheckResult fun <T> View.bindToLifecycle(): LifecycleTransformer<T> {
    return RxLifecycleAndroid.bindView(this)
}