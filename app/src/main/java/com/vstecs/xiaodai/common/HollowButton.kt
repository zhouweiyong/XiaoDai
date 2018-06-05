package com.vstecs.xiaodai.common

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import com.vstecs.xiaodai.R
import com.vstecs.xiaodai.base.color
import com.vstecs.xiaodai.base.drawable

/**
 * Created by zwy on 2018/6/1.
 * email:16681805@qq.com
 */
class HollowButton(context: Context, attributeSet: AttributeSet?) : Button(context, attributeSet) {
    init {
        background = context.drawable(R.drawable.button_hollow_blue_circle);
        setTextColor(context.color(R.color.color_5C8CFF));
    }
}