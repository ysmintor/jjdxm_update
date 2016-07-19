package com.dou361.jjdxm_update;

import android.content.Context;
import android.util.Log;

import com.dou361.update.ParseData;
import com.dou361.update.UpdateHelper;
import com.dou361.update.bean.Update;
import com.dou361.update.util.UpdateSP;

import java.util.Random;
import java.util.TreeMap;

import cn.freedom.cloud.CloudApiModuleCenter;

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

    private static String checkUrl = "http://115.159.45.251:8080/software/v2/index";
    private static String onlineUrl = "http://www.baidu.com";
//    private static String apkFile = "http://wap.apk.anzhi.com/data3/apk/201512/20/55089e385f6e9f350e6455f995ca3452_26503500.apk";
    private static String apkFile = "http://115.159.45.251/software/feibei_live1.0.0.16070810_zs.apk";

    public static void init(Context context) {
        UpdateHelper.init(context);
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("pkname", "com.jingwang.eluxue_live");
//        params.put("pkname", "test.test");
        params.put("Action", "Apps");
        params.put("SecretId", "d021e4f5tac98U4df5Nb943Odd3a313d9f68");
        params.put("Region", "gz");
        params.put("Nonce", Integer.valueOf((new Random()).nextInt(2147483647)));
        params.put("Timestamp", Long.valueOf(System.currentTimeMillis() / 1000L));
        params.put("RequestClient", "SDK_JAVA_1.0");
        try {
            params.put("Signature", Sign.sign(Sign.makeSignPlainText(params, "POST"), "FDC9BC1AA4B387CEBBF0F9355CEC2086"));
        } catch (Exception var9) {
            var9.printStackTrace();
        }
        UpdateHelper.getInstance()
                // 可填：请求方式
                .setMethod(UpdateHelper.RequestType.post)
                // 必填：数据更新接口
                .setCheckUrl(checkUrl, params)
                // 可填：在线参数接口
                .setOnlineUrl(onlineUrl)
                // 必填：用于从数据更新接口获取的数据response中。解析出Update实例。以便框架内部处理
                .setCheckJsonParser(new ParseData() {
                    @Override
                    public Update parse(String response) {
                        Log.e("dou36123", response + "");
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

    public static void ccc() {
        new Thread() {
            @Override
            public void run() {
                TreeMap<String, Object> config = new TreeMap<String, Object>();
                config.put("SecretId", "d021e4f5tac98U4df5Nb943Odd3a313d9f68");
                config.put("SecretKey", "FDC9BC1AA4B387CEBBF0F9355CEC2086");
                config.put("RequestMethod", "POST");
                config.put("DefaultRegion", "gz");
                config.put("Debug", true);

                CloudApiModuleCenter module = new CloudApiModuleCenter(new cn.freedom.cloud.module.Update(), config);

                TreeMap<String, Object> params = new TreeMap<String, Object>();
                params.put("pkname", "com.jingwang.eluxue_live");
//		        params.put("vcode", 1);

                try {
                    System.out.println(module.call("Apps", params));
                } catch (Exception e) {
                    e.printStackTrace();
                }


//                TreeMap<String, Object> config = new TreeMap<String, Object>();
//                config.put("SecretId", "d021e4f5tac98U4df5Nb943Odd3a313d9f68");
//                config.put("SecretKey", "FDC9BC1AA4B387CEBBF0F9355CEC2086");
//                config.put("RequestMethod", "POST");
//                config.put("DefaultRegion", "gz");
//                config.put("Debug", true);
////
//
//                CloudApiModuleCenter module = new CloudApiModuleCenter(new cn.freedom.cloud.module.Update(), config);
//
//                TreeMap<String, Object> params = new TreeMap<String, Object>();
////                params.put("pkname", "test.test");
//                params.put("pkname", "com.jingwang.eluxue_live");
//                try {
//                    String ss = module.call("Apps", params);
//                    Log.e("dou361", ss + "----");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        }.start();
    }

}
