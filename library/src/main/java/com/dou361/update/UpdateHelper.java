package com.dou361.update;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.dou361.update.bean.Update;
import com.dou361.update.listener.DownloadListener;
import com.dou361.update.listener.UpdateListener;
import com.dou361.update.util.NetworkUtil;
import com.dou361.update.view.DialogDownloadUI;
import com.dou361.update.view.DialogUI;

/**
 */
public class UpdateHelper {

    private Context mContext;
    private String checkUrl;
    private String onlineUrl;
    private UpdateStrategy strategy;
    private DialogUI dialogUI;
    private DialogDownloadUI dialogDownloadUI;
    private ParseData parserCheckJson;
    private ParseData parserOnlineJson;

    private static UpdateHelper instance;
    private UpdateListener mUpdateListener;
    private DownloadListener mDownloadListener;

    public static UpdateHelper getInstance() {
        if (instance == null) {
            throw new RuntimeException("UpdateHelper not initialized!");
        } else {
            return instance;
        }
    }

    public static void init(Context appContext) {
        instance = new UpdateHelper(appContext);
    }

    public UpdateHelper(Context context) {
        this.mContext = context;
    }

    public UpdateHelper setCheckUrl(String url) {
        this.checkUrl = url;
        return this;
    }

    public UpdateHelper setOnlineUrl(String url) {
        this.onlineUrl = url;
        return this;
    }

    public UpdateHelper setUpdateListener(UpdateListener listener) {
        this.mUpdateListener = listener;
        return this;
    }

    public UpdateHelper setDownListener(DownloadListener listener) {
        this.mDownloadListener = listener;
        return this;
    }

    public UpdateHelper setCheckJsonParser(ParseData jsonParser) {
        this.parserCheckJson = jsonParser;
        return this;
    }

    public UpdateHelper setOnlineJsonParser(ParseData jsonParser) {
        this.parserOnlineJson = jsonParser;
        return this;
    }

    public UpdateHelper strategy(UpdateStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    public Context getContext() {
        if (mContext == null) {
            throw new RuntimeException("should call UpdateConfig.install first");
        }
        return mContext;
    }

    public UpdateStrategy getStrategy() {
        if (strategy == null) {
            strategy = new UpdateStrategy() {
                boolean isWifi;

                @Override
                public boolean isShowUpdateDialog(Update update) {
                    isWifi = NetworkUtil.isConnectedByWifi();
                    if (isWifi) {
                        return false;
                    }
                    return true;
                }

                @Override
                public boolean isAutoInstall() {
                    return false;
                }

                @Override
                public boolean isShowInstallDialog() {
                    if (isWifi) {
                        return true;
                    }
                    return false;
                }

                @Override
                public boolean isShowDownloadDialog() {
                    if (isWifi) {
                        return false;
                    }
                    return true;
                }
            };
        }
        return strategy;
    }

    public String getCheckUrl() {
        if (TextUtils.isEmpty(checkUrl)) {
            throw new IllegalArgumentException("checkUrl is null");
        }
        return checkUrl;
    }

    public String getOnlineUrl() {
        return onlineUrl;
    }

    public DialogUI getDialogUI() {
        if (dialogUI == null) {
            dialogUI = new DialogUI();
        }
        return dialogUI;
    }

    public DialogDownloadUI getDialogDownloadUI() {
        if (dialogDownloadUI == null) {
            dialogDownloadUI = new DialogDownloadUI();
        }
        return dialogDownloadUI;
    }

    public ParseData getCheckJsonParser() {
        if (parserCheckJson == null) {
            throw new IllegalStateException("update parser is null");
        }
        return parserCheckJson;
    }

    public ParseData getOnlineJsonParser() {
        return parserOnlineJson;
    }


    public void check(Activity activity) {
        Updater.getInstance().checkUpdate(activity);
    }

    public UpdateListener getUpdateListener() {
        return mUpdateListener;
    }

    public DownloadListener getDownloadListener() {
        return mDownloadListener;
    }
}
