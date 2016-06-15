package com.dou361.update.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.dou361.update.UpdateHelper;

/**
 * ========================================
 * <p>
 * 版 权：dou361.com 版权所有 （C） 2015
 * <p>
 * 作 者：陈冠明
 * <p>
 * 个人网站：http://www.dou361.com
 * <p>
 * 版 本：1.0
 * <p>
 * 创建日期：2016/6/14
 * <p>
 * 描 述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public class UpdateSP {


    public static final String KEY_DOWN_SIZE = "update_download_size";

    public static long getLastDownloadSize(String url) {
        SharedPreferences sp = UpdateHelper.getInstance().getContext().getSharedPreferences(KEY_DOWN_SIZE, Context.MODE_PRIVATE);
        return sp.getLong(url, 0);
    }

    public static long getLastDownloadTotalSize(String url) {
        SharedPreferences sp = UpdateHelper.getInstance().getContext().getSharedPreferences(KEY_DOWN_SIZE, Context.MODE_PRIVATE);
        return sp.getLong(url + "_total_size", 0);
    }

    public static boolean isIgnore(String version) {
        SharedPreferences sp = UpdateHelper.getInstance().getContext().getSharedPreferences(KEY_DOWN_SIZE, Context.MODE_PRIVATE);
        return sp.getString("_update_version_ignore", "").equals(version);
    }

    public static boolean isForced() {
        SharedPreferences sp = UpdateHelper.getInstance().getContext().getSharedPreferences(KEY_DOWN_SIZE, Context.MODE_PRIVATE);
        return sp.getBoolean("_update_version_forced", false);
    }

    public static void saveDownloadSize(String url, long size) {
        SharedPreferences sp = UpdateHelper.getInstance().getContext().getSharedPreferences(KEY_DOWN_SIZE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(url, size);
        editor.commit(); // editor.apply() 是异步提交修改 同时修改造成死锁 ANR
    }

    public static void saveDownloadTotalSize(String url, long totalSize) {
        SharedPreferences sp = UpdateHelper.getInstance().getContext().getSharedPreferences(KEY_DOWN_SIZE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(url + "_total_size", totalSize);
        editor.commit();
    }

    public static void setIgnore(String version) {
        SharedPreferences sp = UpdateHelper.getInstance().getContext().getSharedPreferences(KEY_DOWN_SIZE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("_update_version_ignore", version);
        editor.commit();
    }

    public static void setForced(boolean def) {
        SharedPreferences sp = UpdateHelper.getInstance().getContext().getSharedPreferences(KEY_DOWN_SIZE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("_update_version_forced", def);
        editor.commit();
    }
}
