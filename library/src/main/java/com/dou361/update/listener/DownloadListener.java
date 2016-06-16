package com.dou361.update.listener;

import java.io.File;

/**
 * @author Administrator
 */
public interface DownloadListener {

    void onUpdateStart();

    void onUpdateComplete(File file);

    void onUpdateProgress(long current, long total);

    void onUpdateError(int code, String errorMsg);


}
