package com.dou361.update;

import android.content.Context;
import android.text.TextUtils;

import com.dou361.update.business.DefaultDownloadWorker;
import com.dou361.update.business.DefaultUpdateWorker;
import com.dou361.update.business.DownloadWorker;
import com.dou361.update.business.UpdateWorker;
import com.dou361.update.callback.UpdateCheckCB;
import com.dou361.update.callback.UpdateDownloadCB;
import com.dou361.update.creator.DefaultFileCreator;
import com.dou361.update.creator.DefaultNeedDownloadCreator;
import com.dou361.update.creator.DialogUI;
import com.dou361.update.model.DataParser;
import com.dou361.update.strategy.UpdateStrategy;
import com.dou361.update.strategy.WifiFirstStrategy;

/**
 */
public class UpdateHelper {

    private Context context;
    private UpdateWorker checkWorker;
    private DownloadWorker downloadWorker;
    private UpdateCheckCB checkCB;
    private UpdateDownloadCB downloadCB;
    private String checkUrl;
    private String onlineUrl;
    private UpdateStrategy strategy;
    private DialogUI dialogUI;
    private DefaultNeedDownloadCreator downloadDialogCreator;
    private DataParser parserCheckJson;
    private DataParser parserOnlineJson;
    private DefaultFileCreator fileCreator;

    private static UpdateHelper config;

    public static UpdateHelper getInstance() {
        if (config == null) {
            config = new UpdateHelper();
        }
        return config;
    }

    UpdateHelper context(Context context) {
        if (this.context == null) {
            this.context = context.getApplicationContext();
        }
        return this;
    }

    public UpdateHelper checkUrl(String url) {
        this.checkUrl = url;
        return this;
    }

    public UpdateHelper onlineUrl(String url) {
        this.onlineUrl = url;
        return this;
    }

    public UpdateHelper checkWorker(UpdateWorker checkWorker) {
        this.checkWorker = checkWorker;
        return this;
    }

    public UpdateHelper downloadWorker(DownloadWorker downloadWorker) {
        this.downloadWorker = downloadWorker;
        return this;
    }

    public UpdateHelper downloadCB(UpdateDownloadCB downloadCB) {
        this.downloadCB = downloadCB;
        return this;
    }

    public UpdateHelper checkCB(UpdateCheckCB checkCB) {
        this.checkCB = checkCB;
        return this;
    }

    public UpdateHelper parserCheckJson(DataParser jsonParser) {
        this.parserCheckJson = jsonParser;
        return this;
    }

    public UpdateHelper parserOnlineJson(DataParser jsonParser) {
        this.parserOnlineJson = jsonParser;
        return this;
    }

    public UpdateHelper fileCreator(DefaultFileCreator fileCreator) {
        this.fileCreator = fileCreator;
        return this;
    }

    public UpdateHelper downloadDialogCreator(DefaultNeedDownloadCreator downloadDialogCreator) {
        this.downloadDialogCreator = downloadDialogCreator;
        return this;
    }

    public UpdateHelper strategy(UpdateStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    public Context getContext() {
        if (context == null) {
            throw new RuntimeException("should call UpdateConfig.install first");
        }
        return context;
    }

    public UpdateStrategy getStrategy() {
        if (strategy == null) {
            strategy = new WifiFirstStrategy();
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

    public DefaultNeedDownloadCreator getDownloadDialogCreator() {
        if (downloadDialogCreator == null) {
            downloadDialogCreator = new DefaultNeedDownloadCreator();
        }
        return downloadDialogCreator;
    }

    public DataParser parserCheckJson() {
        if (parserCheckJson == null) {
            throw new IllegalStateException("update parser is null");
        }
        return parserCheckJson;
    }

    public DataParser parserOnlineJson() {
        return parserOnlineJson;
    }

    public UpdateWorker getCheckWorker() {
        if (checkWorker == null) {
            checkWorker = new DefaultUpdateWorker();
        }
        return checkWorker;
    }

    public DownloadWorker getDownloadWorker() {
        if (downloadWorker == null) {
            downloadWorker = new DefaultDownloadWorker();
        }
        return downloadWorker;
    }

    public DefaultFileCreator getFileCreator() {
        if (fileCreator == null) {
            fileCreator = new DefaultFileCreator();
        }
        return fileCreator;
    }

    public UpdateCheckCB getCheckCB() {
        return checkCB;
    }

    public UpdateDownloadCB getDownloadCB() {
        return downloadCB;
    }
}
