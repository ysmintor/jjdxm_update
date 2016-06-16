package com.dou361.update.listener;

import com.dou361.update.bean.Update;

/**
 * The update check callback
 */
public interface UpdateListener {

    /**
     * There are a new version of APK on network
     */
    void hasUpdate(Update update);

    /**
     * There are no new version for update
     */
    void noUpdate();

    /**
     * http check error,
     * @param code http code
     * @param errorMsg http error msg
     */
    void onCheckError(int code, String errorMsg);

    /**
     * to be invoked by user press cancel button.
     */
    void onUserCancel();
}
