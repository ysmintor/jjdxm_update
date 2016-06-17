
package com.dou361.update.server;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.dou361.update.R;
import com.dou361.update.UpdateHelper;
import com.dou361.update.bean.Update;
import com.dou361.update.download.DownloadManager;
import com.dou361.update.download.ParamsManager;
import com.dou361.update.util.UpdateConstants;
import com.dou361.update.view.UpdateDialogActivity;

import java.io.File;

/**
 * ========================================
 * <p>
 * 版 权：dou361.com 版权所有 （C） 2015
 * <p>
 * 作 者：陈冠明
 * <p>
 * 个人网站：http://www.dou361.com
 * <p>
 * 版 本：1.0
 * <p>
 * 创建日期：2016/6/16 23:25
 * <p>
 * 描 述：原理
 * 纵线
 * 首先是点击更新时，弹出进度对话框（进度，取消和运行在后台），
 * 如果是在前台完成下载，弹出安装对话框，
 * 如果是在后台完成下载，通知栏提示下载完成，
 * 横线
 * 如果进入后台后，还在继续下载点击时重新回到原界面
 * 如果强制更新无进入后台功能
 * 如果是静默更新，安静的安装
 * <p>
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public class DownloadingService extends Service {

    private RemoteViews contentView;
    private NotificationManager notificationManager;
    private Notification notification;
    private Update update;
    private NotificationCompat.Builder ntfBuilder;
    private String url;
    private Context mContext;
    private DownloadManager manage;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String strUrl = (String) msg.obj;
            if (url != null && url.equals(strUrl)) {
                switch (msg.what) {
                    case ParamsManager.State_NORMAL:
                        break;
                    case ParamsManager.State_DOWNLOAD:
                        Bundle bundle = msg.getData();
                        long current = bundle.getLong("percent");
                        long total = bundle.getLong("loadSpeed");
                        notifyNotification(current, total);
                        break;
                    case ParamsManager.State_FINISH:
                        File fil = new File(manage.getDownPath(), url.substring(url.lastIndexOf("/") + 1, url.length()));
                        showInstallNotificationUI(fil);
                        if (UpdateHelper.getInstance().getUpdateType() == UpdateHelper.UpdateType.autowifidown) {
                            installApk(mContext, fil);
                        } else {
                            Intent intent = new Intent(mContext, UpdateDialogActivity.class);
                            intent.putExtra(UpdateConstants.DATA_UPDATE, update);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(UpdateConstants.DATA_ACTION, UpdateConstants.UPDATE_INSTALL);
                            intent.putExtra(UpdateConstants.SAVE_PATH, fil.getAbsolutePath());
                            startActivity(intent);
                        }
                        break;
                    case ParamsManager.State_PAUSE:
                        break;
                    case ParamsManager.State_WAIT:
                        createNotification();
                        break;
                }
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = this;
        manage = DownloadManager.getInstance(mContext);
        manage.setHandler(handler);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            int action = intent.getIntExtra(UpdateConstants.DATA_ACTION, 0);
            if (action == UpdateConstants.START_DOWN) {
                update = (Update) intent.getSerializableExtra(UpdateConstants.DATA_UPDATE);
                url = update.getUpdateUrl();
                if (update != null && !TextUtils.isEmpty(url)) {
                    manage.startDownload(url);
                }
            } else if (action == UpdateConstants.PAUSE_DOWN) {
                if (manage.isDownloading(url)) {
                    manage.pauseDownload(url);
                } else {
                    manage.startDownload(url);
                }
            } else if (action == UpdateConstants.CANCEL_DOWN) {
                manage.deleteDownload(url);
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @SuppressWarnings("deprecation")
    public void createNotification() {
        notification = new Notification(
                getApplicationInfo().icon,
                "安装包正在下载...",
                System.currentTimeMillis());
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        /*** 自定义  Notification 的显示****/
        contentView = new RemoteViews(getPackageName(), R.layout.jjdxm_download_notification);
        contentView.setTextViewText(R.id.jjdxm_update_title, getApplicationInfo().name);
        contentView.setProgressBar(R.id.jjdxm_update_progress_bar, 100, 0, false);
        contentView.setTextViewText(R.id.jjdxm_update_progress_text, "0%");

        /**暂停和开始*/
        Intent pauseIntent = new Intent(this, DownloadingService.class);
        pauseIntent.putExtra(UpdateConstants.DATA_ACTION, UpdateConstants.PAUSE_DOWN);
        pauseIntent.putExtra("update", update);
        PendingIntent pendingIntent1 = PendingIntent.getService(this, 0, pauseIntent, 0);
        contentView.setOnClickPendingIntent(R.id.jjdxm_update_rich_notification_continue, pendingIntent1);

        /**取消*/
        Intent cancelIntent = new Intent(this, DownloadingService.class);
        cancelIntent.putExtra(UpdateConstants.DATA_ACTION, UpdateConstants.CANCEL_DOWN);
        cancelIntent.putExtra("update", update);
        PendingIntent pendingIntent2 = PendingIntent.getService(this, 0, cancelIntent, 0);
        contentView.setOnClickPendingIntent(R.id.jjdxm_update_rich_notification_cancel, pendingIntent2);

        notification.contentView = contentView;
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(UpdateConstants.NOTIFICATION_ACTION, notification);
    }

    private void notifyNotification(long percent, long length) {
        contentView.setTextViewText(R.id.jjdxm_update_progress_text, (percent * 100 / length) + "%");
        contentView.setProgressBar(R.id.jjdxm_update_progress_bar, (int) length, (int) percent, false);
        notification.contentView = contentView;
        notificationManager.notify(UpdateConstants.NOTIFICATION_ACTION, notification);
    }

    private void showInstallNotificationUI(File file) {
        if (ntfBuilder == null) {
            ntfBuilder = new NotificationCompat.Builder(this);
        }
        ntfBuilder.setSmallIcon(getApplicationInfo().icon)
                .setContentTitle(getApplicationInfo().name)
                .setContentText("下载完成，点击安装").setTicker("任务下载完成");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(
                Uri.fromFile(file),
                "application/vnd.android.package-archive");
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, 0);
        ntfBuilder.setContentIntent(pendingIntent);
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        notificationManager.notify(UpdateConstants.NOTIFICATION_ACTION,
                ntfBuilder.build());
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