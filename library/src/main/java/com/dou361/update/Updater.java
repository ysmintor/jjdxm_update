package com.dou361.update;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.dou361.update.bean.Update;
import com.dou361.update.http.DownloadWorker;
import com.dou361.update.http.IUpdateExecutor;
import com.dou361.update.http.UpdateExecutor;
import com.dou361.update.http.UpdateWorker;
import com.dou361.update.listener.DownloadListener;
import com.dou361.update.listener.UpdateListener;
import com.dou361.update.server.DownloadingService;
import com.dou361.update.util.DialogSafeOperator;
import com.dou361.update.util.FileUtils;
import com.dou361.update.util.InstallUtil;
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

    private static final int DOWNLOAD_NOTIFICATION_ID = 0x3;


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
                    Intent intent = new Intent(activity,DownloadingService.class);
                    intent.putExtra("update", update);
                    activity.startService(intent);
//                    Updater.getInstance().downUpdate(actRef.get(), update);
                    return;
                }

                DialogUI creator = UpdateHelper.getInstance().getDialogUI();
                creator.setCheckCB(this);
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

    private ProgressDialog dialogDowning;

    /**
     * Request to download apk.
     *
     * @param activity The activity who need to show download and install dialog;
     * @param update   update instance,should not be null;
     */
    public void downUpdate(final Activity activity, final Update update) {
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
                    DialogSafeOperator.safeShowDialog(dialogDowning);
                }

            }

            @Override
            public void onUpdateComplete(File file) {
                if (mDownload != null) {
                    mDownload.onUpdateComplete(file);
                }
                DialogSafeOperator.safeDismissDialog(dialogDowning);
                if (UpdateHelper.getInstance().getStrategy().isShowInstallDialog()) {
                    DialogUI creator = UpdateHelper.getInstance().getDialogUI();
                    Dialog dialog = creator.create(1, update, file.getAbsolutePath(), actRef.get());
                    DialogSafeOperator.safeShowDialog(dialog);
                    showInstallNotificationUI(activity, file.getAbsolutePath());
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
                showDownloadNotificationUI(activity, percent);
            }

            @Override
            public void onUpdateError(int code, String errorMsg) {
                if (mDownload != null) {
                    mDownload.onUpdateError(code, errorMsg);
                }
                DialogSafeOperator.safeDismissDialog(dialogDowning);
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
    private void showDownloadNotificationUI(Activity activity, final int progress) {
        NotificationManager manager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        //构建通知
        Notification notification = new Notification();
        notification.icon = activity.getApplicationInfo().icon;
        notification.tickerText = "开始下载...";
        String contentText = new StringBuffer().append(progress)
                .append("%").toString();
        //加载自定义布局
        RemoteViews contentView = new RemoteViews(activity.getPackageName(), R.layout.jjdxm_download_notification);
        //通知显示的布局
        notification.contentView = contentView;
        //设置值
        contentView.setProgressBar(R.id.jjdxm_update_progress_bar, 100, progress, false);
        contentView.setTextViewText(R.id.jjdxm_update_title, activity.getApplicationInfo().name);
        contentView.setTextViewText(R.id.jjdxm_update_progress_text, contentText);
//        //点击的事件处理
//        Intent buttonIntent = new Intent(ACTION_BUTTON);
//        /* 播放/暂停  按钮 */
//        buttonIntent.putExtra(INTENT_BUTTONID_TAG, R.id.jjdxm_update_rich_notification_continue);
//        PendingIntent intent_paly = PendingIntent.getBroadcast(this, 2, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_play, intent_paly);
//        /* 下一首 按钮  */
//        buttonIntent.putExtra(INTENT_BUTTONID_TAG, R.id.jjdxm_update_rich_notification_cancel);
//        PendingIntent intent_next = PendingIntent.getBroadcast(this, 3, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_next, intent_next);
        //发送通知
        manager.notify(DOWNLOAD_NOTIFICATION_ID, notification);


//        if (activity != null) {
//            String contentText = new StringBuffer().append(progress)
//                    .append("%").toString();
//            PendingIntent contentIntent = PendingIntent.getActivity(activity,
//                    0, new Intent(), PendingIntent.FLAG_CANCEL_CURRENT);
//            if (notificationManager == null) {
//                notificationManager = (NotificationManager) activity
//                        .getSystemService(Context.NOTIFICATION_SERVICE);
//            }
//            if (ntfBuilder == null) {
//                ntfBuilder = new NotificationCompat.Builder(activity)
//                        .setSmallIcon(activity.getApplicationInfo().icon)
//                        .setTicker("开始下载...")
//                        .setContentTitle(activity.getApplicationInfo().name)
//                        .setContentIntent(contentIntent);
//            }
//            ntfBuilder.setContentText(contentText);
//            ntfBuilder.setProgress(100, progress, false);
//            notificationManager.notify(DOWNLOAD_NOTIFICATION_ID,
//                    ntfBuilder.build());
//        }
    }

    private void showInstallNotificationUI(Activity activity,
                                           String appPath) {
        if (ntfBuilder == null) {
            ntfBuilder = new NotificationCompat.Builder(activity);
        }
        ntfBuilder.setSmallIcon(activity.getApplicationInfo().icon)
                .setContentTitle(activity.getApplicationInfo().name)
                .setContentText("下载完成，点击安装").setTicker("任务下载完成");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(
                Uri.parse("file://" + appPath),
                "application/vnd.android.package-archive");
        PendingIntent pendingIntent = PendingIntent.getActivity(
                activity, 0, intent, 0);
        ntfBuilder.setContentIntent(pendingIntent);
        if (notificationManager == null) {
            notificationManager = (NotificationManager) activity
                    .getSystemService(Context.NOTIFICATION_SERVICE);
        }
        notificationManager.notify(DOWNLOAD_NOTIFICATION_ID,
                ntfBuilder.build());
    }

}
