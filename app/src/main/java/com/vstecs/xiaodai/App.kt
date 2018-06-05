package com.vstecs.xiaodai

import android.app.Application
import com.facebook.stetho.Stetho
import com.github.moduth.blockcanary.BlockCanary
import com.github.moduth.blockcanary.BlockCanaryContext
import com.squareup.leakcanary.LeakCanary
import com.uuzuche.lib_zxing.activity.ZXingLibrary
import com.vstecs.xiaodai.base.ILog

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) return
        LeakCanary.install(this)
        BlockCanary.install(this, BlockCanaryContext()).start()
        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)
        ILog.initialize(BuildConfig.DEBUG)
        ZXingLibrary.initDisplayOpinion(this);
    }

    companion object {
        inline fun debug(fn: () -> Unit) {
            if (BuildConfig.DEBUG) fn()
        }
    }
}
