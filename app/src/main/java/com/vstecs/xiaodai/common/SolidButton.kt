package com.vstecs.xiaodai.common

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import com.vstecs.xiaodai.R
import com.vstecs.xiaodai.base.color
import com.vstecs.xiaodai.base.drawable

/**
 * Created by zwy on 2018/5/30.
 * email:16681805@qq.com
 */
class SolidButton(context: Context,attributeSet: AttributeSet?=null):Button(context,attributeSet){
    init {
        background = context.drawable(R.drawable.button_solid_blue_circle);
        setTextColor(context.color(R.color.white));
    }

}