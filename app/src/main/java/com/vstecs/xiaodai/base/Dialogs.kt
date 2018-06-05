package com.vstecs.xiaodai.base

import android.app.AlertDialog
import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View

fun Context.alert(@LayoutRes layoutId: Int, fn: AlertDialog.Builder.(View) -> Unit): AlertDialog {
    return AlertDialog.Builder(this).apply {
        val contentView = LayoutInflater.from(context).inflate(layoutId, null, false)
        setView(contentView)
        fn(contentView)
    }.create().apply { show() }
}