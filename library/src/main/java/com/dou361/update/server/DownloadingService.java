
package com.dou361.update.server;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.dou361.update.R;
import com.dou361.update.bean.Update;
import com.dou361.update.http.DownloadWorker;
import com.dou361.update.http.UpdateExecutor;
import com.dou361.update.listener.DownloadListener;
import com.dou361.update.util.FileUtils;

import java.io.File;

public class DownloadingService extends Service {

    private RemoteViews contentView;
    private NotificationManager notificationManager;
    private Notification notification;
    private Update update;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            update = (Update) intent.getSerializableExtra("update");
            if (update != null && !TextUtils.isEmpty(update.getUpdateUrl())) {
                downApk();
            } else {
//                mHandler.sendEmptyMessage(URL_ERROR);
            }

        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void downApk() {
        DownloadWorker downloadWorker = new DownloadWorker();
        downloadWorker.setUrl(update.getUpdateUrl());
        downloadWorker.setDownloadListener(new DownloadListener() {
            @Override
            public void onUpdateStart() {
                createNotification();
            }

            @Override
            public void onUpdateComplete(File file) {
                notifyNotification(100, 100);
//                if (UpdateHelper.getInstance().getStrategy().isShowInstallDialog()) {
//                    DialogUI creator = UpdateHelper.getInstance().getDialogUI();
//                    Dialog dialog = creator.create(1, update, file.getAbsolutePath(), actRef.get());
//                    DialogSafeOperator.safeShowDialog(dialog);
//                    showInstallNotificationUI(activity, file.getAbsolutePath());
//                } else if (UpdateHelper.getInstance().getStrategy().isAutoInstall()) {
//                    installApk(UpdateHelper.getInstance().getContext(), file);
//                }
            }

            @Override
            public void onUpdateProgress(long current, long total) {
                notifyNotification(current, total);
            }

            @Override
            public void onUpdateError(int code, String errorMsg) {
            }
        });
        downloadWorker.setCacheFileName(FileUtils.createFile(update.getVersionName()));
        UpdateExecutor.getInstance().download(downloadWorker);
    }

    @SuppressWarnings("deprecation")
    public void createNotification() {
        notification = new Notification(
                getApplicationInfo().icon,
                "安装包正在下载...",
                System.currentTimeMillis());
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        //notification.flags = Notification.FLAG_AUTO_CANCEL;

        /*** 自定义  Notification 的显示****/
        contentView = new RemoteViews(getPackageName(), R.layout.jjdxm_download_notification);
        contentView.setProgressBar(R.id.jjdxm_update_progress_bar, 100, 0, false);
        contentView.setTextViewText(R.id.jjdxm_update_progress_text, "0%");
        notification.contentView = contentView;

        /*updateIntent = new Intent(this, AboutActivity.class);
          updateIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
     	updateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
     	pendingIntent = PendingIntent.getActivity(this, 0, updateIntent, 0);
      	notification.contentIntent = pendingIntent;*/
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //设置notification的PendingIntent
        /*Intent intt = new Intent(this, MainActivity.class);
		PendingIntent pi = PendingIntent.getActivity(this,100, intt,Intent.FLAG_ACTIVITY_NEW_TASK	| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		notification.contentIntent = pi;*/
        notificationManager.notify(R.layout.jjdxm_download_notification, notification);
    }

    private void notifyNotification(long percent, long length) {
        contentView.setTextViewText(R.id.jjdxm_update_progress_text, (percent * 100 / length) + "%");
        contentView.setProgressBar(R.id.jjdxm_update_progress_bar, (int) length, (int) percent, false);
        notification.contentView = contentView;
        notificationManager.notify(R.layout.jjdxm_download_notification, notification);
    }

    /**
     * 安装apk
     *
     * @param context 上下文
     * @param file    APK文件
     */
    public static void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}