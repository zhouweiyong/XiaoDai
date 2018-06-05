package com.vstecs.xiaodai.order.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.vstecs.xiaodai.R
import com.vstecs.xiaodai.base.BaseActivity

class OrderDetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)
    }

    companion object {
        fun start(context: Context) {
            Intent(context, OrderDetailsActivity::class.java)
                .let { context.startActivity(it) }
        }
    }
}