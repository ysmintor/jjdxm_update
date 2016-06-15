package com.dou361.update.callback;

import android.app.Activity;
import android.app.Dialog;

import com.dou361.update.UpdateBuilder;
import com.dou361.update.Updater;
import com.dou361.update.creator.DialogUI;
import com.dou361.update.model.Update;
import com.dou361.update.util.SafeDialogOper;
import com.dou361.update.util.UpdateSP;

import java.lang.ref.WeakReference;

/**
 * default callback that check if new version exist
 */
public class DefaultCheckCB implements UpdateCheckCB {

    private WeakReference<Activity> actRef = null;
    private UpdateBuilder builder;
    private UpdateCheckCB checkCB;

    public DefaultCheckCB(Activity context) {
        this.actRef = new WeakReference<>(context);
    }

    public void setBuilder(UpdateBuilder builder) {
        this.builder = builder;
        checkCB = builder.getCheckCB();
    }

    @Override
    public void hasUpdate(Update update) {
        if (checkCB != null) {
            checkCB.hasUpdate(update);
        }

        if (!builder.getStrategy().isShowUpdateDialog(update)) {
            Updater.getInstance().downUpdate(actRef.get(), update, builder);
            return;
        }

        DialogUI creator = builder.getDialogUI();
        creator.setBuilder(builder);
        creator.setCheckCB(this);
        Dialog dialog = creator.create(0, update, null, actRef.get());

        if (UpdateSP.isForced()) {
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
        }
        SafeDialogOper.safeShowDialog(dialog);
    }

    @Override
    public void noUpdate() {
        if (checkCB != null) {
            checkCB.noUpdate();
        }
    }

    @Override
    public void onCheckError(int code, String errorMsg) {
        if (checkCB != null) {
            checkCB.onCheckError(code, errorMsg);
        }
    }

    @Override
    public void onUserCancel() {
        if (checkCB != null) {
            checkCB.onUserCancel();
        }
    }
}
