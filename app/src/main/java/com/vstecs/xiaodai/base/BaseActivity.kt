package com.vstecs.xiaodai.base

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.annotation.CallSuper
import android.support.annotation.CheckResult
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.RxLifecycleAndroid
import com.vstecs.xiaodai.R
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

abstract class BaseActivity : AppCompatActivity(), LifecycleProvider<ActivityEvent>, ILog {
    private val lifecycleSubject = BehaviorSubject.create<ActivityEvent>()

    @CallSuper override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(ActivityEvent.CREATE)
        when (getOverridePendingTransitionMode()) {
            TransitionMode.RIGHT -> overridePendingTransition(R.anim.left_in, R.anim.left_out)
            TransitionMode.LEFT -> overridePendingTransition(R.anim.right_in, R.anim.right_out)
            TransitionMode.TOP -> overridePendingTransition(R.anim.top_in, R.anim.top_out)
            TransitionMode.BOTTOM -> overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out)
            TransitionMode.SCALE -> overridePendingTransition(R.anim.scale_in, R.anim.scale_out)
            TransitionMode.FADE -> overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            else -> {
            }
        }

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setTranslucentStatus(isApplyStatusBarTranslucency())
    }

    @CallSuper override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(ActivityEvent.START)
    }

    @CallSuper override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(ActivityEvent.RESUME)
    }

    @CallSuper override fun onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE)
        super.onPause()
    }

    @CallSuper override fun onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP)
        super.onStop()
    }

    @CallSuper override fun onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY)
        super.onDestroy()
    }

    @CheckResult override fun lifecycle(): Observable<ActivityEvent> {
        return lifecycleSubject.hide()
    }

    @CheckResult override fun <T> bindUntilEvent(event: ActivityEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent<T, ActivityEvent>(lifecycleSubject, event)
    }

    @CheckResult override fun <T> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroid.bindActivity<T>(lifecycleSubject)
    }

    protected open fun getOverridePendingTransitionMode(): TransitionMode = TransitionMode.DEFAULT

    protected open fun isApplyStatusBarTranslucency(): Boolean = false

    override fun finish() {
        super.finish()
        when (getOverridePendingTransitionMode()) {
            BaseActivity.TransitionMode.RIGHT -> overridePendingTransition(R.anim.right_in, R.anim.right_out)
            BaseActivity.TransitionMode.LEFT -> overridePendingTransition(R.anim.left_in, R.anim.left_out)
            BaseActivity.TransitionMode.TOP -> overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out)
            BaseActivity.TransitionMode.BOTTOM -> overridePendingTransition(R.anim.top_in, R.anim.top_out)
            BaseActivity.TransitionMode.SCALE -> overridePendingTransition(R.anim.scale_in_disappear, R.anim.scale_out_disappear)
            BaseActivity.TransitionMode.FADE -> overridePendingTransition(R.anim.fade_in_disappear, R.anim.fade_out_disappear)
            else -> {
            }
        }
    }

    /**
     * set status bar translucency
     *
     * @param on
     */
    private fun setTranslucentStatus(on: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val win = window
            val winParams = win.attributes
            val bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            if (on) {
                winParams.flags = winParams.flags or bits
            } else {
                winParams.flags = winParams.flags and bits.inv()
            }
            win.attributes = winParams
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.windowToken)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.height
            val right = left + v.width
            return !(event.x > left && event.x < right && event.y > top && event.y < bottom)
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private fun hideKeyboard(token: IBinder?) {
        if (token != null) {
            val im = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    enum class TransitionMode {
        DEFAULT, LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }
}
