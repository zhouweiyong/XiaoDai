package com.vstecs.xiaodai.login

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.tbruyelle.rxpermissions2.RxPermissions
import com.uuzuche.lib_zxing.activity.CodeUtils
import com.vstecs.xiaodai.R
import com.vstecs.xiaodai.base.BaseActivity
import com.vstecs.xiaodai.base.ILog
import com.vstecs.xiaodai.contract.SignContractActivity
import kotlinx.android.synthetic.main.activity_login.*


/**
 * Created by zwy on 2018/5/30.
 * email:16681805@qq.com
 */
class LoginActivity : BaseActivity(), View.OnClickListener {
    val REQUEST_CODE = 0x110;

    override fun getOverridePendingTransitionMode(): TransitionMode {
        return TransitionMode.DEFAULT;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login);


        btn_login_al.setOnClickListener(this);
        btn_saoma_al.setOnClickListener(this);

        requestPermissions();
    }

    private fun requestPermissions() {
        val rxPermission = RxPermissions(this@LoginActivity);
        rxPermission
                .requestEach(
                        Manifest.permission.CAMERA
                )
                .subscribe { permission ->
                    if (permission.granted) {
                        // 用户已经同意该权限
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                    } else {
                        // 用户拒绝了该权限，并且选中『不再询问』
                    }
                }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_login_al -> {
                ILog.debug("登录");
                startActivity(Intent(this@LoginActivity, SignContractActivity::class.java));
            }
            R.id.btn_saoma_al -> {
                ILog.debug("扫码登录");

                var intent: Intent = Intent(this, QRCodeActivity::class.java);
                startActivityForResult(intent, REQUEST_CODE);
            }
            else -> {

            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                val bundle = data.extras;
                ; if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    val result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(this@LoginActivity, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(this@LoginActivity, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}