package com.dou361.update;

import com.dou361.update.bean.Update;

/**
 * @author lzh
 */
public class UpdateStrategy {

    /**
     * whether or not to show update dialog
     */
    public boolean isShowUpdateDialog(Update update) {
        return false;
    }

    /**
     * whether or not automatic installation without show install dialog
     */
    public boolean isAutoInstall() {
        return false;
    }

    /**
     * whether or not to show install dialog
     */
    public boolean isShowInstallDialog() {
        return false;
    }

    /**
     * whether or not to show download dialog
     */
    public boolean isShowDownloadDialog() {
        return false;
    }
}
