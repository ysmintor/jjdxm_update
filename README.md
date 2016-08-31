
# [jjdxm_update][project] #

### Copyright notice ###

我在网上写的文章、项目都可以转载，但请注明出处，这是我唯一的要求。当然纯我个人原创的成果被转载了，不注明出处也是没有关系的，但是由我转载或者借鉴了别人的成果的请注明他人的出处，算是对前辈们的一种尊重吧！

虽然我支持写禁止转载的作者，这是他们的成果，他们有这个权利，但我不觉得强行扭转用户习惯会有一个很好的结果。纯属个人的观点，没有特别的意思。可能我是一个版权意识很差的人吧，所以以前用了前辈们的文章、项目有很多都没有注明出处，实在是抱歉！有想起或看到的我都会逐一补回去。

从一开始，就没指望从我写的文章、项目上获得什么回报，一方面是为了自己以后能够快速的回忆起曾经做过的事情，避免重复造轮子做无意义的事，另一方面是为了锻炼下写文档、文字组织的能力和经验。如果在方便自己的同时，对你们也有很大帮助，自然是求之不得的事了。要是有人转载或使用了我的东西觉得有帮助想要打赏给我，多少都行哈，心里却很开心，被人认可总归是件令人愉悦的事情。

站在了前辈们的肩膀上，才能走得更远视野更广。前辈们写的文章、项目给我带来了很多知识和帮助，我没有理由不去努力，没有理由不让自己成长的更好。写出好的东西于人于己都是好的，但是由于本人自身视野和能力水平有限，错误或者不好的望多多指点交流。

项目中如有不同程度的参考借鉴前辈们的文章、项目会在下面注明出处的，纯属为了个人以后开发工作或者文档能力的方便。如有侵犯到您的合法权益，对您造成了困惑，请联系协商解决，望多多谅解哈！若您也有共同的兴趣交流技术上的问题加入交流群QQ： 548545202

感谢作者[shelwee][author]，本项目是基于[UpdateHelper][url]项目

## Introduction ##

应用内更新，实现类似友盟自动更新sdk的更新模式，用户使用前只需要配置自己的服务器更新检查接口即可（必须接口），也可以拓展加入一个接口作为在线参数配置来实现（可选接口）可实现以下四种种方式更新和是否强制更新组合使用，支持get、post方式请求网络，默认是使用get方式

## 更新检查 ##
### 1.手动更新：手动检测更新（所有网络类型环境检测并提示主要用于点击检测使用）  ###
### 2.自动更新：自动检测更新（所有网络类型环境检测并提示） ###
### 3.仅WiFi自动检测更新（只有WiFi网络类型环境检测并提示） ###
### 4.静默更新：仅WiFi自动检测下载（只有WiFi网络类型环境检测、下载完才提示） ###
## 强制更新 ##
强制更新（配合在线参数使得当前版本无法使用）结合以上几种方式组合使用，主要使用场景是当上一个版本的APP有重大bug或漏洞时，修改在线参数统一控制所有的APP用户，使得之前的所有版本必须要升级才能正常使用。主要原理：服务器上修改参数的数值，APP端获取后进行判断，如果为强制更新，则在打开应用是提示有新版APP更新，更新完成才能使用，提示框不消失，用户如果选择不更新则退出应用。


## Features ##
### 1.更新数据接口用户可自定义 ###
### 2.在线参数接口用户可自定义 ###
### 3.参数结构返回可以让用户自定义解析 ###
## Screenshots ##

<img src="https://raw.githubusercontent.com/jjdxmashl/jjdxm_update/master/screenshots/icon01.gif" width="300">
<img src="https://raw.githubusercontent.com/jjdxmashl/jjdxm_update/master/screenshots/icon02.gif" width="300">
<img src="https://raw.githubusercontent.com/jjdxmashl/jjdxm_update/master/screenshots/icon01.png" width="300"> 
<img src="https://raw.githubusercontent.com/jjdxmashl/jjdxm_update/master/screenshots/icon02.png" width="300"> 
<img src="https://raw.githubusercontent.com/jjdxmashl/jjdxm_update/master/screenshots/icon03.png" width="300">
<img src="https://raw.githubusercontent.com/jjdxmashl/jjdxm_update/master/screenshots/icon04.png" width="300">
<img src="https://raw.githubusercontent.com/jjdxmashl/jjdxm_update/master/screenshots/icon05.png" width="300">

## Download ##

[demo apk下载][downapk]

Download or grab via Maven:

	<dependency>
	  <groupId>com.dou361.update</groupId>
	  <artifactId>jjdxm-update</artifactId>
	  <version>x.x.x</version>
	</dependency>

or Gradle:

	compile 'com.dou361.update:jjdxm-update:x.x.x'

历史版本：

	compile 'com.dou361.update:jjdxm-update:1.0.3'
	compile 'com.dou361.update:jjdxm-update:1.0.2'
	compile 'com.dou361.update:jjdxm-update:1.0.1'
	compile 'com.dou361.update:jjdxm-update:1.0.0'


jjdxm-update requires at minimum Java 15 or Android 4.0.

[架包的打包引用以及冲突解决][jaraar]

## Proguard ##

根据你的混淆器配置和使用，您可能需要在你的proguard文件内配置以下内容：

	后续加上

[AndroidStudio代码混淆注意的问题][minify]

## Get Started ##

### 需要权限 ###

	<!--jjdxm_update更新 start-->
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <!--jjdxm_update更新 end-->

清单文件中需要配置一个activity和一个服务

	<!--jjdxm_update更新 start-->
    <activity
        android:name="com.dou361.update.view.UpdateDialogActivity"
        android:theme="@android:style/Theme.Translucent.NoTitleBar" >
    </activity>

    <service android:name="com.dou361.update.server.DownloadingService"/>
    <!--jjdxm_update更新 end-->

### 1.在Application中配置，初始化配置接口和解析参数 ###


这里必须配置一个在线更新接口及其的数据返回结构的解析，可选的是在线参数接口及其数据返回结构的解析，在线参数可以随机定义零个或多个不同意义的参数来达到在线修改apk的部分特性。

（1）创建一个自动更新的配置文件

    public class UpdateConfig {

        private static String checkUrl = "http://115.159.45.251:8080/software/v2/index";
        private static String onlineUrl = "http://www.baidu.com";
        private static String apkFile = "http://wap.apk.anzhi.com/data3/apk/201512/20/55089e385f6e9f350e6455f995ca3452_26503500.apk";

	    public static void init(Context context) {
	        UpdateHelper.init(context);
	        UpdateHelper.getInstance()
	                // 必填：数据更新接口
	                .setCheckUrl(checkUrl)
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
	                });
	    }
	}

在Application的oncreate方法中调用
    
	init(this);

配置接口和解析数据

get请求，默认为get请求

	private static String checkUrl = "你的更新接口";
    private static String onlineUrl = "你的在线参数接口";
	//临时使用的下载地址
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


post请求

	private static String checkUrl = "你的更新接口";
    private static String onlineUrl = "你的在线参数接口";
	public static void init(Context context) {
        UpdateHelper.init(context);
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("pkname", "com.jingwang.eluxue_online");
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
                        // 此处模拟一个Update对象
                        Update update = new Update();
                        try {
                            JSONObject jobj = new JSONObject(response);
                            if (!jobj.isNull("data")) {
                                JSONObject job = jobj.optJSONObject("data");
                                if (!job.isNull("v_code")) {
                                    // 此apk包的版本号
                                    update.setVersionCode(Integer.valueOf(job.optString("v_code")));
                                }
                                if (!job.isNull("v_size")) {
                                    // 此apk包的大小
                                    update.setApkSize(job.optLong("v_size"));
                                }
                                if (!job.isNull("v_name")) {
                                    // 此apk包的版本名称
                                    update.setVersionName(job.optString("v_name"));
                                }
                                if (!job.isNull("update_content")) {
                                    // 此apk包的更新内容
                                    update.setUpdateContent(job.optString("update_content"));
                                }
                                if (!job.isNull("download_url")) {
                                    // 此apk包的下载地址
                                    update.setUpdateUrl(job.optString("download_url"));
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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

### 2.在mainActivity中oncreate()方法中调用 ###
	//默认是自动检测更新
	UpdateHelper.getInstance()
                .check(MainActivity.this);

### 3.在需要手动点击的方法中调用 ###

	//手动检测更新
	UpdateHelper.getInstance()
                .setUpdateType(UpdateHelper.UpdateType.checkupdate)
                .setUpdateListener(new UpdateListener() {
                    @Override
                    public void noUpdate() {
                        Toast.makeText(mContext, "已经是最新版本了", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCheckError(int code, String errorMsg) {
                        Toast.makeText(mContext, "检测更新失败：" + errorMsg, Toast.LENGTH_LONG).show();
                    }
                })
                .check(MainActivity.this);

### 4.几种方式的调用 ###

	//有网更新
	UpdateHelper.UpdateType.checkupdate,
	//自动更新
    UpdateHelper.UpdateType.autoupdate,
	//只有WiFi更新
    UpdateHelper.UpdateType.autowifiupdate,
	//只有WiFi下载
    UpdateHelper.UpdateType.autowifidown

调用方式这里只举例静默更新，其他方式类似

	/静默更新
	UpdateHelper.getInstance()
				.setUpdateType(UpdateHelper.UpdateType.autowifidown)
                .check(MainActivity.this);

### 5.更新监听回调UpdateListener，主要有四个方法 ###

	/**
     * There are a new version of APK on network
     */
    public void hasUpdate(Update update) {

    }

    /**
     * There are no new version for update
     */
    public abstract void noUpdate();

    /**
     * http check error,
     *
     * @param code     http code
     * @param errorMsg http error msg
     */
    public abstract void onCheckError(int code, String errorMsg);

    /**
     * to be invoked by user press cancel button.
     */
    public void onUserCancel() {

    }


## More Actions ##

## ChangeLog ##

2016.07.29 修复07.28打包post请求方式造成的get请求需要传params集合问题。下一个版本会接入在线参数和强制更新功能
2016.07.28修复通知栏暂停取消和进度显示问题，增加post请求方式获取数据。

2016.06.20修复通知栏提示报错问题，修改v7.jar依赖方式，让用户自己去配置版本。

2016.06.17当前版本只有四种更新方式，可以支持断点续传。

## About Author ##

#### 个人网站:[http://www.dou361.com][web] ####
#### GitHub:[jjdxmashl][github] ####
#### QQ:316988670 ####
#### 交流QQ群:548545202 ####


## License ##

    Copyright (C) dou361, The Framework Open Source Project
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
     	http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

## (Frequently Asked Questions)FAQ ##
## Bugs Report and Help ##

If you find any bug when using project, please report [here][issues]. Thanks for helping us building a better one.




[web]:http://www.dou361.com
[github]:https://github.com/jjdxmashl/
[project]:https://github.com/jjdxmashl/jjdxm_update/
[issues]:https://github.com/jjdxmashl/jjdxm_update/issues/new
[downapk]:https://raw.githubusercontent.com/jjdxmashl/jjdxm_update/master/apk/app-debug.apk
[lastaar]:https://raw.githubusercontent.com/jjdxmashl/jjdxm_update/master/release/jjdxm-update-1.0.3.aar
[lastjar]:https://raw.githubusercontent.com/jjdxmashl/jjdxm_update/master/release/jjdxm-update-1.0.3.jar
[icon01]:https://raw.githubusercontent.com/jjdxmashl/jjdxm_update/master/screenshots/icon01.png
[icon02]:https://raw.githubusercontent.com/jjdxmashl/jjdxm_update/master/screenshots/icon02.png
[jaraar]:https://github.com/jjdxmashl/jjdxm_ecodingprocess/blob/master/架包的打包引用以及冲突解决.md
[minify]:https://github.com/jjdxmashl/jjdxm_ecodingprocess/blob/master/AndroidStudio代码混淆注意的问题.md
[author]:https://github.com/shelwee
[url]:https://github.com/shelwee/UpdateHelper