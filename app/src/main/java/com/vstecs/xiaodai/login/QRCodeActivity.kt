package com.vstecs.xiaodai.login

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import com.uuzuche.lib_zxing.activity.CaptureFragment
import com.uuzuche.lib_zxing.activity.CodeUtils
import com.vstecs.xiaodai.R
import com.vstecs.xiaodai.base.BaseActivity



/**
 * Created by zwy on 2018/5/30.
 * email:16681805@qq.com
 */
class QRCodeActivity : BaseActivity() {
    private lateinit var captureFragment:CaptureFragment;
    override fun getOverridePendingTransitionMode(): TransitionMode {
        return TransitionMode.DEFAULT;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode);
        captureFragment = CaptureFragment();
        CodeUtils.setFragmentArgs(captureFragment,R.layout.layout_qrcode);
        captureFragment.analyzeCallback = analyzeCallback;
        supportFragmentManager.beginTransaction().replace(R.id.fl_my_container,captureFragment).commit();
    }


    val analyzeCallback = object : CodeUtils.AnalyzeCallback {
        override fun onAnalyzeSuccess(mBitmap: Bitmap?, result: String?) {
            val resultIntent = Intent()
            val bundle = Bundle()
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS)
            bundle.putString(CodeUtils.RESULT_STRING, result)
            resultIntent.putExtras(bundle)
            this@QRCodeActivity.setResult(Activity.RESULT_OK, resultIntent)
            this@QRCodeActivity.finish()
        }

        override fun onAnalyzeFailed() {
            val resultIntent = Intent()
            val bundle = Bundle()
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED)
            bundle.putString(CodeUtils.RESULT_STRING, "")
            resultIntent.putExtras(bundle)
            this@QRCodeActivity.setResult(Activity.RESULT_OK, resultIntent)
            this@QRCodeActivity.finish()
        }

    }
}