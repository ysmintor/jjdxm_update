package com.dou361.update.creator;

import android.app.Activity;

import com.dou361.update.callback.UpdateDownloadCB;
import com.dou361.update.model.Update;

/**
 * @author Administrator
 */
public interface DownloadCreator {
    UpdateDownloadCB create(Update update, Activity activity);
}
