package com.dou361.update.util;

import android.content.Context;

import com.dou361.update.UpdateHelper;

import java.io.File;

/**
 * ========================================
 * 
 * 版 权：dou361.com 版权所有 （C） 2015
 * 
 * 作 者：陈冠明
 * 
 * 个人网站：http://www.dou361.com
 * 
 * 版 本：1.0
 * 
 * 创建日期：2016/6/16
 * 
 * 描 述：
 * 
 * 
 * 修订历史：
 * 
 * ========================================
 */
public class FileUtils {
    public static File createFile(String versionName) {
        File cacheDir = getCacheDir();
        cacheDir.mkdirs();
        return new File(cacheDir, "update_v_" + versionName);
    }

    private static File getCacheDir() {
        Context context = UpdateHelper.getInstance().getContext();
        File cacheDir = context.getExternalCacheDir();
        if (cacheDir == null) {
            cacheDir = context.getCacheDir();
        }
        cacheDir = new File(cacheDir, "update");
        return cacheDir;
    }
}
