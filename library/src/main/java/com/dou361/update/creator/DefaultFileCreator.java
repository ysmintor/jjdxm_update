package com.dou361.update.creator;

import android.content.Context;

import com.dou361.update.UpdateHelper;

import java.io.File;

/**
 * @author Administrator
 */
public class DefaultFileCreator {
    public File create(String versionName) {
        File cacheDir = getCacheDir();
        cacheDir.mkdirs();
        return new File(cacheDir, "update_v_" + versionName);
    }

    File getCacheDir() {
        Context context = UpdateHelper.getInstance().getContext();
        File cacheDir = context.getExternalCacheDir();
        if (cacheDir == null) {
            cacheDir = context.getCacheDir();
        }
        cacheDir = new File(cacheDir, "update");
        return cacheDir;
    }
}
