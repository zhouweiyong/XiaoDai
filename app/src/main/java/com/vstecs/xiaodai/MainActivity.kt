package com.vstecs.xiaodai

import android.os.Bundle
import cn.thens.kdroid.vadapter.*
import com.vstecs.xiaodai.base.BaseActivity
import com.vstecs.xiaodai.order.list.OrderListActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private val orderAdapter: VPagerAdapter<Int> by lazy {
        pagerAdapter<Int>(R.layout.view_order_card) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vOrderList.adapter = orderAdapter
        orderAdapter.refill(0..9)
        vOrderList.offscreenPageLimit = 3
        vOrderListBackground.setOnTouchListener { _, event ->
            vOrderList.dispatchTouchEvent(event)
        }
        vOrderList.setPageTransformer(true) { view, _ ->
            view.scaleX = 0.96F
            view.scaleY = 0.96F
        }

        vToOrderList.setOnClickListener { OrderListActivity.start(this) }
    }
}
