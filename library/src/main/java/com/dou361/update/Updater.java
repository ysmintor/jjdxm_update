package com.dou361.update;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;

import com.dou361.update.bean.Update;
import com.dou361.update.http.IUpdateExecutor;
import com.dou361.update.http.UpdateExecutor;
import com.dou361.update.http.UpdateWorker;
import com.dou361.update.listener.UpdateListener;
import com.dou361.update.server.DownloadingService;
import com.dou361.update.util.DialogSafeOperator;
import com.dou361.update.util.UpdateConstants;
import com.dou361.update.util.UpdateSP;
import com.dou361.update.view.DialogUI;

import java.lang.ref.WeakReference;

public class Updater {
    private static Updater updater;
    private IUpdateExecutor executor;

    private Updater() {
        executor = UpdateExecutor.getInstance();
    }

    public static Updater getInstance() {
        if (updater == null) {
            updater = new Updater();
        }
        return updater;
    }


    /**
     * check out whether or not there is a new version on internet
     *
     * @param activity The activity who need to show update dialog
     */
    public void onlineGet(Activity activity) {

    }

    /**
     * check out whether or not there is a new version on internet
     *
     * @param activity The activity who need to show update dialog
     */
    public void checkUpdate(final Activity activity) {
        UpdateWorker checkWorker = new UpdateWorker();
        checkWorker.setUrl(UpdateHelper.getInstance().getCheckUrl());
        checkWorker.setParser(UpdateHelper.getInstance().getCheckJsonParser());

        final UpdateListener mUpdate = UpdateHelper.getInstance().getUpdateListener();
        final WeakReference<Activity> actRef = new WeakReference<>(activity);
        checkWorker.setUpdateListener(new UpdateListener() {
            @Override
            public void hasUpdate(Update update) {
                if (mUpdate != null) {
                    mUpdate.hasUpdate(update);
                }
                if (!UpdateHelper.getInstance().getStrategy().isShowUpdateDialog(update)) {
                    Intent intent = new Intent(activity, DownloadingService.class);
                    intent.putExtra(UpdateConstants.SERVER_ACTION, UpdateConstants.START_DOWN);
                    intent.putExtra("update", update);
                    activity.startService(intent);
//                    Updater.getInstance().downUpdate(actRef.get(), update);
                    return;
                }

                DialogUI creator = UpdateHelper.getInstance().getDialogUI();
                creator.setUpdateListener(this);
                Dialog dialog = creator.create(0, update, null, actRef.get());

                if (UpdateSP.isForced()) {
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);
                }
                DialogSafeOperator.safeShowDialog(dialog);
            }

            @Override
            public void noUpdate() {
                if (mUpdate != null) {
                    mUpdate.noUpdate();
                }
            }

            @Override
            public void onCheckError(int code, String errorMsg) {
                if (mUpdate != null) {
                    mUpdate.onCheckError(code, errorMsg);
                }
            }

            @Override
            public void onUserCancel() {
                if (mUpdate != null) {
                    mUpdate.onUserCancel();
                }
            }
        });
        executor.check(checkWorker);
    }

}
