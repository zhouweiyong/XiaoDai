package com.vstecs.xiaodai.contract

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.app.FragmentManager
import android.os.Bundle
import com.vstecs.xiaodai.R
import com.vstecs.xiaodai.base.XLog
import kotlinx.android.synthetic.main.dialog_verify_contract.view.*

/**
 * Created by zwy on 2018/6/4.
 * email:16681805@qq.com
 */
class ContractVerifyDialog : DialogFragment() {

    var click: VerifyDialogClick? = null;


    fun show(fragmentManager: FragmentManager) {
        show(fragmentManager, "ContractVerifyDialog");
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity);
        val inflater = activity.layoutInflater;
        val view = inflater.inflate(R.layout.dialog_verify_contract, null);
        builder.setView(view);
        view.btn_cancel_dv.setOnClickListener {
            dismiss();
        }
        view.iv_verify_code_dv.setImageBitmap(Code.getInstance().createBitmap());
        view.llyt_refresh_dv.setOnClickListener({ v ->
            view.iv_verify_code_dv.setImageBitmap(Code.getInstance().createBitmap());
        })
        view.btn_get_code_dv.setOnClickListener {
            XLog.i("dialog>>>getCode");
            click?.onPhoneCodeClick();
        };
        view.btn_comfirm_dv.setOnClickListener {
            XLog.i("dialog>>>comfirm");
            click?.onComfirm(view.et_phone_code_dv.text.toString());
        };
        val dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }


    interface VerifyDialogClick {
        fun onPhoneCodeClick();
        fun onComfirm(phoneCode: String);
    }


}