package com.vstecs.xiaodai.contract

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.app.FragmentManager
import android.os.Bundle
import com.vstecs.xiaodai.R

/**
 * Created by zwy on 2018/6/5.
 * email:16681805@qq.com
 */
class ContractCancelDialog : DialogFragment() {

    var lister: OnCancelLister? = null;

    fun show(fragmentManager: FragmentManager) {
        show(fragmentManager, "ContractVerifyDialog");
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity);
        val inflater = activity.layoutInflater;
        val view = inflater.inflate(R.layout.dialog_cancel_contract, null);
        val dialog = builder.create();
        return dialog;
    }


    interface OnCancelLister {
        fun cancelContract();
    }
}