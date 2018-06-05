package com.vstecs.xiaodai.common

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.vstecs.xiaodai.R
import com.vstecs.xiaodai.base.color
import com.vstecs.xiaodai.base.drawable
import kotlinx.android.synthetic.main.layout_contract_title.view.*

/**
 * Created by zwy on 2018/5/31.
 * email:16681805@qq.com
 */
class ContractTitle(context: Context, attrs: AttributeSet? = null) : RelativeLayout(context, attrs), View.OnClickListener {


    init {
        LayoutInflater.from(context).inflate(R.layout.layout_contract_title, this);
        btn_left_lct.setOnClickListener(this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }


    fun setProcess(process: Int) {
        if (process > 1) {
            tv_two_lct.background = context.drawable(R.drawable.oval_bg_select);
            view_one_lct.setBackgroundColor(context.color(R.color.white));
        }
        if (process > 2) {
            tv_three_lct.background = context.drawable(R.drawable.oval_bg_select);
            view_two_lct.setBackgroundColor(context.color(R.color.white));
        }
    }


    fun setTitle(title: String? = "填写标题") {
        tv_title_lct.setText(title);
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_left_lct -> {
                (context as Activity).finish();
            }
            else -> {

            }
        }
    }


}