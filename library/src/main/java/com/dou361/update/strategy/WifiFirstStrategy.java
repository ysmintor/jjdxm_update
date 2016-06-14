package com.dou361.update.strategy;

import com.dou361.update.model.Update;
import com.dou361.update.util.NetworkUtil;

/**
 * @author Administrator
 */
public class WifiFirstStrategy implements UpdateStrategy {

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
}
