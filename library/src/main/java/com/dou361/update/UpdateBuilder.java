package com.dou361.update;

import android.app.Activity;
import android.text.TextUtils;

import com.dou361.update.business.DownloadWorker;
import com.dou361.update.business.UpdateWorker;
import com.dou361.update.callback.UpdateCheckCB;
import com.dou361.update.callback.UpdateDownloadCB;
import com.dou361.update.creator.DefaultFileCreator;
import com.dou361.update.creator.DefaultNeedDownloadCreator;
import com.dou361.update.creator.DialogUI;
import com.dou361.update.model.DataParser;
import com.dou361.update.strategy.UpdateStrategy;

/**
 * @author Administrator
 */
public class UpdateBuilder {

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

    public static UpdateBuilder create() {
        return new UpdateBuilder();
    }

    public UpdateBuilder url(String url) {
        this.checkUrl = url;
        return this;
    }

    public UpdateBuilder checkWorker(UpdateWorker checkWorker) {
        this.checkWorker = checkWorker;
        return this;
    }

    public UpdateBuilder downloadWorker(DownloadWorker downloadWorker) {
        this.downloadWorker = downloadWorker;
        return this;
    }

    public UpdateBuilder downloadCB(UpdateDownloadCB downloadCB) {
        this.downloadCB = downloadCB;
        return this;
    }

    public UpdateBuilder checkCB(UpdateCheckCB checkCB) {
        this.checkCB = checkCB;
        return this;
    }

    public UpdateBuilder fileCreator(DefaultFileCreator fileCreator) {
        this.fileCreator = fileCreator;
        return this;
    }

    public UpdateBuilder strategy(UpdateStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    public void check(Activity activity) {
        Updater.getInstance().onlineGet(activity, this);
        Updater.getInstance().checkUpdate(activity, this);
    }

    public UpdateStrategy getStrategy() {
        if (strategy == null) {
            strategy = UpdateHelper.getInstance().getStrategy();
        }
        return strategy;
    }

    public String getCheckUrl() {
        if (TextUtils.isEmpty(checkUrl)) {
            checkUrl = UpdateHelper.getInstance().getCheckUrl();
        }
        return checkUrl;
    }

    public String getOnlineUrl() {
        if (TextUtils.isEmpty(onlineUrl)) {
            onlineUrl = UpdateHelper.getInstance().getOnlineUrl();
        }
        return onlineUrl;
    }

    public DialogUI getDialogUI() {
        if (dialogUI == null) {
            dialogUI = UpdateHelper.getInstance().getDialogUI();
        }
        return dialogUI;
    }

    public DefaultNeedDownloadCreator getDownloadDialogCreator() {
        if (downloadDialogCreator == null) {
            downloadDialogCreator = UpdateHelper.getInstance().getDownloadDialogCreator();
        }
        return downloadDialogCreator;
    }

    public DataParser parserCheckJson() {
        if (parserCheckJson == null) {
            parserCheckJson = UpdateHelper.getInstance().parserCheckJson();
        }
        return parserCheckJson;
    }

    public DataParser parserOnlineJson() {
        if (parserOnlineJson == null) {
            parserOnlineJson = UpdateHelper.getInstance().parserOnlineJson();
        }
        return parserOnlineJson;
    }

    public UpdateWorker getCheckWorker() {
        if (checkWorker == null) {
            checkWorker = UpdateHelper.getInstance().getCheckWorker();
        }
        return checkWorker;
    }

    public DownloadWorker getDownloadWorker() {
        if (downloadWorker == null) {
            downloadWorker = UpdateHelper.getInstance().getDownloadWorker();
        }
        return downloadWorker;
    }

    public DefaultFileCreator getFileCreator() {
        if (fileCreator == null) {
            fileCreator = UpdateHelper.getInstance().getFileCreator();
        }
        return fileCreator;
    }

    public UpdateCheckCB getCheckCB() {
        if (checkCB == null) {
            checkCB = UpdateHelper.getInstance().getCheckCB();
        }
        return checkCB;
    }

    public UpdateDownloadCB getDownloadCB() {
        if (downloadCB == null) {
            downloadCB = UpdateHelper.getInstance().getDownloadCB();
        }
        return downloadCB;
    }
}
