package com.vstecs.xiaodai.base

import android.util.Log

/**
 * Created by zwy on 2018/6/1.
 * email:16681805@qq.com
 */
open class XLog{
    val TAG:String = "XiaoDai";

    fun i(s:String){
        Log.i(TAG,s);
    }
    companion object: XLog() {

    }
}