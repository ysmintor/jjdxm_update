package com.dou361.update.view;

import android.app.Activity;
import android.app.ProgressDialog;

import com.dou361.update.bean.Update;
import com.dou361.update.util.SafeDialogOper;
import com.dou361.update.util.UpdateSP;

/**
 * @author Administrator
 */
public class DialogDownloadUI {


    public ProgressDialog create(Update update, Activity activity) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMax(100);
        dialog.setProgress(0);
        if (UpdateSP.isForced()) {
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
        }
        SafeDialogOper.safeShowDialog(dialog);
        return dialog;
    }
}
