package com.dou361.update.creator;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.dou361.update.callback.UpdateDownloadCB;
import com.dou361.update.model.Update;
import com.dou361.update.util.SafeDialogOper;
import com.dou361.update.util.UpdateSP;

import java.io.File;

/**
 * @author Administrator
 */
public class DefaultNeedDownloadCreator {

    private NotificationManager notificationManager;
    private NotificationCompat.Builder ntfBuilder;

    public UpdateDownloadCB create(Update update, Activity activity) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMax(100);
        dialog.setProgress(0);
        if (UpdateSP.isForced()) {
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
        }
        SafeDialogOper.safeShowDialog(dialog);
        UpdateDownloadCB downloadCB = new UpdateDownloadCB() {
            @Override
            public void onUpdateStart() {
            }

            @Override
            public void onUpdateComplete(File file) {
                SafeDialogOper.safeDismissDialog(dialog);
            }

            @Override
            public void onUpdateProgress(long current, long total) {
                int percent = (int) (current * 1.0f / total * 100);
                dialog.setProgress(percent);
            }

            @Override
            public void onUpdateError(int code, String errorMsg) {
                SafeDialogOper.safeDismissDialog(dialog);
            }
        };
        return downloadCB;
    }

    /**
     * 通知栏弹出下载提示进度
     * @param progress
     */
    private void showDownloadNotificationUI(String appName, Activity activity,
                                            final int progress) {
        if (activity != null) {
            String contentText = new StringBuffer().append(progress)
                    .append("%").toString();
            PendingIntent contentIntent = PendingIntent.getActivity(activity,
                    0, new Intent(), PendingIntent.FLAG_CANCEL_CURRENT);
            if (notificationManager == null) {
                notificationManager = (NotificationManager) activity
                        .getSystemService(Context.NOTIFICATION_SERVICE);
            }
            if (ntfBuilder == null) {
                ntfBuilder = new NotificationCompat.Builder(activity)
                        .setSmallIcon(activity.getApplicationInfo().icon)
                        .setTicker("开始下载...")
                        .setContentTitle(appName)
                        .setContentIntent(contentIntent);
            }
            ntfBuilder.setContentText(contentText);
            ntfBuilder.setProgress(100, progress, false);
            notificationManager.notify(1,
                    ntfBuilder.build());
        }
    }
}
