package com.dou361.jjdxm_update;

import android.content.Context;

import com.dou361.update.ParseData;
import com.dou361.update.UpdateHelper;
import com.dou361.update.bean.Update;
import com.dou361.update.util.UpdateSP;

/**
 * ========================================
 * <p/>
 * 版 权：dou361.com 版权所有 （C） 2015
 * <p/>
 * 作 者：陈冠明
 * <p/>
 * 个人网站：http://www.dou361.com
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期：2016/6/14
 * <p/>
 * 描 述：
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class UpdateConfig {

    private static String checkUrl = "http://www.baidu.com";
    private static String onlineUrl = "http://www.baidu.com";
    private static String apkFile = "http://wap.apk.anzhi.com/data3/apk/201512/20/55089e385f6e9f350e6455f995ca3452_26503500.apk";

    public static void init(Context context) {
        UpdateHelper.init(context);
        UpdateHelper.getInstance()
                // 必填：数据更新接口
                .setCheckUrl(checkUrl)
                // 可填：在线参数接口
                .setOnlineUrl(onlineUrl)
                // 必填：用于从数据更新接口获取的数据response中。解析出Update实例。以便框架内部处理
                .setCheckJsonParser(new ParseData() {
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
                .setOnlineJsonParser(new ParseData() {
                    @Override
                    public String parse(String httpResponse) {
                        return null;
                    }
                });
    }

}
