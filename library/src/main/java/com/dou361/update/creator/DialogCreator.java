package com.dou361.update.creator;

import android.app.Activity;
import android.app.Dialog;

import com.dou361.update.UpdateBuilder;
import com.dou361.update.Updater;
import com.dou361.update.callback.UpdateCheckCB;
import com.dou361.update.model.Update;

/**
 *
 */
public abstract class DialogCreator {

    private UpdateBuilder builder;
    private UpdateCheckCB checkCB;
    public void setBuilder(UpdateBuilder builder) {
        this.builder = builder;
    }

    public void setCheckCB(UpdateCheckCB checkCB) {
        this.checkCB = checkCB;
    }

    public abstract Dialog create(Update update,Activity context);

    public void sendDownloadRequest(Update update,Activity activity) {
        Updater.getInstance().downUpdate(activity,update,builder);
    }

    public void sendUserCancel() {
        if (this.checkCB != null) {
            this.checkCB.onUserCancel();
        }
    }
}