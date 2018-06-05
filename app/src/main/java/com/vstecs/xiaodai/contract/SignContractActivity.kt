package com.vstecs.xiaodai.contract

import android.os.Bundle
import android.view.View
import com.vstecs.xiaodai.R
import com.vstecs.xiaodai.base.BaseActivity
import com.vstecs.xiaodai.base.XLog
import kotlinx.android.synthetic.main.activity_sign_contract.*


/**
 * Created by zwy on 2018/5/31.
 * email:16681805@qq.com
 */
class SignContractActivity : BaseActivity(), View.OnClickListener, ContractVerifyDialog.VerifyDialogClick {


    lateinit var code: String;
    val verifyDialog: ContractVerifyDialog
        by lazy {
            ContractVerifyDialog()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_contract);

        initView();

    }

    private fun initView() {
        title_asc.setProcess(2);
        title_asc.setTitle("申请贷款");
        pdf_asc.fromAsset("sample.pdf")
                .enableSwipe(true)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAnnotationRendering(false)
                .password(null)
                .scrollHandle(null)
                .load();

        btn_sign_asc.setOnClickListener(this);
        verifyDialog.click = this;
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_sign_asc -> {
                verifyDialog.show(fragmentManager);
            }
            R.id.btn_nosign_asc -> {

            }

            else -> {

            }
        }
    }

    override fun onPhoneCodeClick() {
        XLog.i("获取验证码！！");
    }

    override fun onComfirm(phoneCode: String) {
        XLog.i("确定》》" + phoneCode);
    }

}