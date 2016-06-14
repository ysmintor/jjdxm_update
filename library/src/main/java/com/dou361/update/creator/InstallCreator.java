package com.dou361.update.creator;

import android.app.Activity;
import android.app.Dialog;

import com.dou361.update.callback.UpdateCheckCB;
import com.dou361.update.model.Update;

/**
 * @author Administrator
 */
public abstract class InstallCreator {

    private UpdateCheckCB checkCB;

    public void setCheckCB(UpdateCheckCB checkCB) {
        this.checkCB = checkCB;
    }

    public abstract Dialog create(Update update,String path,Activity activity);

    public void sendUserCancel() {
        if (this.checkCB != null) {
            this.checkCB.onUserCancel();
        }
    }
}
