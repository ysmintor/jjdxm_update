package com.dou361.update;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.dou361.update.bean.Update;
import com.dou361.update.http.DownloadWorker;
import com.dou361.update.http.IUpdateExecutor;
import com.dou361.update.http.UpdateExecutor;
import com.dou361.update.http.UpdateWorker;
import com.dou361.update.listener.DownloadListener;
import com.dou361.update.listener.UpdateListener;
import com.dou361.update.util.FileUtils;
import com.dou361.update.util.InstallUtil;
import com.dou361.update.util.SafeDialogOper;
import com.dou361.update.util.UpdateSP;
import com.dou361.update.view.DialogDownloadUI;
import com.dou361.update.view.DialogUI;

import java.io.File;
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
    public void checkUpdate(Activity activity) {
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
                    Updater.getInstance().downUpdate(actRef.get(), update);
                    return;
                }

                DialogUI creator = UpdateHelper.getInstance().getDialogUI();
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

    private ProgressDialog dialogDowning;

    /**
     * Request to download apk.
     *
     * @param activity The activity who need to show download and install dialog;
     * @param update   update instance,should not be null;
     */
    public void downUpdate(Activity activity, final Update update) {
        DownloadWorker downloadWorker = new DownloadWorker();
        downloadWorker.setUrl(update.getUpdateUrl());
        final DownloadListener mDownload = UpdateHelper.getInstance().getDownloadListener();
        final WeakReference<Activity> actRef = new WeakReference<>(activity);
        final DialogDownloadUI dialogDownloadUI = UpdateHelper.getInstance().getDialogDownloadUI();

        downloadWorker.setDownloadListener(new DownloadListener() {
            @Override
            public void onUpdateStart() {
                if (mDownload != null) {
                    mDownload.onUpdateStart();
                }
                if (UpdateHelper.getInstance().getStrategy().isShowDownloadDialog()) {
                    dialogDowning = dialogDownloadUI.create(update, actRef.get());
                    SafeDialogOper.safeShowDialog(dialogDowning);
                }

            }

            @Override
            public void onUpdateComplete(File file) {
                if (mDownload != null) {
                    mDownload.onUpdateComplete(file);
                }
                SafeDialogOper.safeDismissDialog(dialogDowning);
                if (UpdateHelper.getInstance().getStrategy().isShowInstallDialog()) {
                    DialogUI creator = UpdateHelper.getInstance().getDialogUI();
                    Dialog dialog = creator.create(1, update, file.getAbsolutePath(), actRef.get());
                    SafeDialogOper.safeShowDialog(dialog);
                } else if (UpdateHelper.getInstance().getStrategy().isAutoInstall()) {
                    InstallUtil.installApk(UpdateHelper.getInstance().getContext(), file.getAbsolutePath());
                }
            }

            @Override
            public void onUpdateProgress(long current, long total) {
                if (mDownload != null) {
                    mDownload.onUpdateProgress(current, total);
                }

                int percent = (int) (current * 1.0f / total * 100);
                if (dialogDowning != null) {
                    dialogDowning.setProgress(percent);
                }
            }

            @Override
            public void onUpdateError(int code, String errorMsg) {
                if (mDownload != null) {
                    mDownload.onUpdateError(code, errorMsg);
                }
                SafeDialogOper.safeDismissDialog(dialogDowning);
            }
        });
        downloadWorker.setCacheFileName(FileUtils.createFile(update.getVersionName()));

        executor.download(downloadWorker);
    }


    private NotificationManager notificationManager;
    private NotificationCompat.Builder ntfBuilder;

    /**
     * 通知栏弹出下载提示进度
     *
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
