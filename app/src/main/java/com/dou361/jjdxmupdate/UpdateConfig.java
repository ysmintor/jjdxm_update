package com.dou361.jjdxmupdate;

import android.content.Context;
import android.widget.Toast;

import com.dou361.update.UpdateHelper;
import com.dou361.update.callback.EmptyDownloadCB;
import com.dou361.update.model.DataParser;
import com.dou361.update.model.Update;
import com.dou361.update.util.UpdateSP;

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
public class UpdateConfig {

    private static String checkUrl = "http://www.baidu.com";
    private static String onlineUrl = "http://www.baidu.com";
    private static String apkFile = "http://apk.hiapk.com/appdown/com.hiapk.live?planid=2515816&seid=c711112f-cc50-0001-a55f-bfe5123fe450";
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
        // UpdateConfig为全局配置。当在其他页面中。使用UpdateBuilder进行检查更新时。
        // 对于没传的参数，会默认使用UpdateConfig中的全局配置
        UpdateHelper.getInstance()
                // 必填：数据更新接口
                .checkUrl(checkUrl)
                // 可填：在线参数接口
                .onlineUrl(onlineUrl)
                // 必填：用于从数据更新接口获取的数据response中。解析出Update实例。以便框架内部处理
                .parserCheckJson(new DataParser() {
                    @Override
                    public Update parse(String response) {
                        // 此处模拟一个Update对象
                        Update update = new Update();
                        // 此apk包的下载地址
                        update.setUpdateUrl(apkFile);
                        // 此apk包的版本号
                        update.setVersionCode(2);
                        update.setApkSize(12400000);
                        // 此apk包的版本名称
                        update.setVersionName("2.0");
                        // 此apk包的更新内容
                        update.setUpdateContent("测试更新");
                        // 此apk包是否为强制更新
                        UpdateSP.setForced(false);
                        return update;
                    }
                })
                // 可填：在线参数接口
                .parserOnlineJson(new DataParser() {
                    @Override
                    public Update parse(String httpResponse) {
                        return null;
                    }
                })
                // 可填：apk下载的回调
                .downloadCB(new EmptyDownloadCB() {
                    @Override
                    public void onUpdateError(int code, String errorMsg) {
                        showUpdateTie(errorMsg);
                    }
                });
    }

    public static void showUpdateTie(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

}
