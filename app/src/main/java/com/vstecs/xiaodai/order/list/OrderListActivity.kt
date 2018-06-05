package com.vstecs.xiaodai.order.list

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.widget.PopupWindowCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import cn.thens.kdroid.vadapter.VAdapter
import cn.thens.kdroid.vadapter.VFastRecyclerAdapter
import cn.thens.kdroid.vadapter.fastRecyclerAdapter
import cn.thens.kdroid.vadapter.toHolder
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshKernel
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.vstecs.xiaodai.R
import com.vstecs.xiaodai.base.*
import com.vstecs.xiaodai.base.view.VsRefreshHeader
import com.vstecs.xiaodai.order.details.OrderDetailsActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_order_list.*
import kotlinx.android.synthetic.main.popup_order_list.view.*
import pl.droidsonroids.gif.GifDrawable
import java.util.concurrent.TimeUnit

class OrderListActivity : BaseActivity() {
    private var isHistory = false

    private val orderListAdapter: VFastRecyclerAdapter<Int> by lazy {
        fastRecyclerAdapter<Int>(R.layout.item_order) {
            setOnClickListener { OrderDetailsActivity.start(this@OrderListActivity) }
        }
    }

    private val productTypePopupWindow: PopupWindow by lazy {
        val productTypeAdapter = object : VFastRecyclerAdapter<String>() {
            override fun createHolder(viewGroup: ViewGroup, viewType: Int): VAdapter.Holder<String> {
                val vText = TextView(viewGroup.context).apply {
                    setBackgroundResource(R.drawable.selector_border_primary)
                    setPadding(dp(26), dp(10), dp(26), dp(10))
                }
                return FrameLayout(viewGroup.context).run {
                    addView(vText)
                    setPadding(dp(10), dp(10), dp(10), dp(10))
                    toHolder {
                        vText.text = it
                    }
                }
            }
        }
        PopupWindow(this).apply {
            val context = this@OrderListActivity
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            isOutsideTouchable = true
            isFocusable = true
            setBackgroundDrawable(ColorDrawable(Color.WHITE))
            contentView = LayoutInflater.from(context).inflate(R.layout.popup_order_list, null, false)
            contentView.vCategoryList.layoutManager = FlexboxLayoutManager(context, FlexDirection.ROW, FlexWrap.WRAP)
            contentView.vCategoryList.adapter = productTypeAdapter
            elevation = 12F
            productTypeAdapter.refill(listOf("接力贷", "订单贷"))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)
        isHistory = intent.getBooleanExtra(K_IS_HISTORY, false)
        if (isHistory) {
            vToolbarTitle.text = "历史订单"
            vToHistory.visibility = View.GONE
        }

        vToolbarNavigation.setOnClickListener { finish() }
        vToHistory.setOnClickListener { startHistory(this) }

        vLoanDateTab.setOnClickListener {

        }
        vProductTypeTab.setOnClickListener {
            PopupWindowCompat.showAsDropDown(productTypePopupWindow, vProductTypeTab, 0, 0, Gravity.START)
        }
        vOrderTypeTab.setOnClickListener {

        }

        vOrderList.layoutManager = LinearLayoutManager(this)
        vOrderList.adapter = orderListAdapter
        vRefreshLayout.setRefreshHeader(VsRefreshHeader(this))
        vRefreshLayout.setOnRefreshListener {
            Observable.timer(2, TimeUnit.SECONDS)
                .doOnNext { vRefreshLayout.finishRefresh() }
                .end()
        }
    }

    override fun onResume() {
        super.onResume()
        orderListAdapter.refill(0..9)
    }

    override fun getOverridePendingTransitionMode() = TransitionMode.RIGHT

    companion object {
        private const val K_IS_HISTORY = "isHistory"

        fun start(context: Context) {
            context.startActivity(Intent(context, OrderListActivity::class.java))
        }

        fun startHistory(context: Context) {
            Intent(context, OrderListActivity::class.java)
                .putExtra(K_IS_HISTORY, true)
                .let { context.startActivity(it) }
        }
    }
}