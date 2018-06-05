package com.vstecs.xiaodai.base

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

interface ILog {
    fun assert(cond: Boolean, text: String) {
        if (!cond) Logger.e(text)
    }

    fun debug(obj: Any?) {
        Logger.d(obj)
    }

    fun error(msg: Any?) {
        Logger.e(msg.toString())
    }

    fun error(e: Throwable?, msg: String = "") {
        Logger.e(e, msg)
    }

    fun verbose(msg: String) {
        Logger.v(msg)
    }

    fun info(msg: String) {
        Logger.i(msg)
    }

    fun warn(msg: String) {
        Logger.w(msg)
    }

    fun wtf(msg: String) {
        Logger.wtf(msg)
    }

    companion object : ILog {
        fun initialize(debug: Boolean) {
            val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)
                .methodCount(1)
                .methodOffset(2)
                .tag("@")
                .build()
            Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
                override fun isLoggable(priority: Int, tag: String?) = debug
            })
        }
    }
}