package com.vstecs.xiaodai.base.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshKernel
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.vstecs.xiaodai.R
import com.vstecs.xiaodai.base.dp
import pl.droidsonroids.gif.GifDrawable

class VsRefreshHeader(context: Context) : LinearLayout(context), RefreshHeader {
    private val pullDrawable: GifDrawable by lazy { GifDrawable(context.resources, R.drawable.refresh_1) }
    private val finishDrawable: GifDrawable by lazy { GifDrawable(context.resources, R.drawable.refresh_2) }
    private val animationView = ImageView(context)

    init {
        animationView.setImageDrawable(pullDrawable)
        animationView.setPadding(0, dp(16), 0, dp(16))
        addView(animationView, LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(72)))
    }

    override fun getSpinnerStyle(): SpinnerStyle = SpinnerStyle.Translate

    override fun getView(): View = this

    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        if (pullDrawable.isRunning) pullDrawable.stop()
        animationView.setImageDrawable(finishDrawable)
        finishDrawable.reset()
        if (!finishDrawable.isRunning) finishDrawable.start()
        return finishDrawable.duration
    }

    override fun onReleased(refreshLayout: RefreshLayout?, height: Int, extendHeight: Int) {
        if (!pullDrawable.isRunning) pullDrawable.start()
    }

    override fun onPulling(percent: Float, offset: Int, height: Int, extendHeight: Int) {
        if (pullDrawable.isRunning) pullDrawable.stop()
        val frameCount = pullDrawable.numberOfFrames
        val frame = 1F * frameCount * offset / extendHeight
        pullDrawable.seekToFrame(frame.toInt() % frameCount)
    }

    override fun onStateChanged(refreshLayout: RefreshLayout, oldState: RefreshState, newState: RefreshState) {
        when (newState) {
            RefreshState.PullDownToRefresh -> {
                if (finishDrawable.isRunning) finishDrawable.stop()
                animationView.setImageDrawable(pullDrawable)
            }
            RefreshState.ReleaseToRefresh -> {
                if (!pullDrawable.isRunning) pullDrawable.start()
            }
        }
    }

    override fun onInitialized(kernel: RefreshKernel, height: Int, extendHeight: Int) {}

    override fun onHorizontalDrag(percentX: Float, offsetX: Int, offsetMax: Int) {}

    override fun isSupportHorizontalDrag(): Boolean = false

    override fun setPrimaryColors(vararg colors: Int) {}

    override fun onReleasing(percent: Float, offset: Int, height: Int, extendHeight: Int) {}

    override fun onStartAnimator(refreshLayout: RefreshLayout, height: Int, extendHeight: Int) {}
}