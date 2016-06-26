
# [jjdxm_update][project] #
## Introduction ##
应用内更新，实现类似友盟自动更新sdk的更新模式，用户使用前只需要配置自己的服务器更新检查接口即可（必须接口），也可以拓展加入一个接口作为在线参数配置来实现（可选接口）可实现以下四种种方式更新和是否强制更新组合使用：
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

<img src="https://raw.githubusercontent.com/jjdxmashl/jjdxm_update/master/screenshots/icon01.png" width="300"> 
<img src="https://raw.githubusercontent.com/jjdxmashl/jjdxm_update/master/screenshots/icon02.png" width="300"> 
<img src="https://raw.githubusercontent.com/jjdxmashl/jjdxm_update/master/screenshots/icon03.png" width="300">
<img src="https://raw.githubusercontent.com/jjdxmashl/jjdxm_update/master/screenshots/icon04.png" width="300">
<img src="https://raw.githubusercontent.com/jjdxmashl/jjdxm_update/master/screenshots/icon05.png" width="300">

## Download ##

[demo apk下载][downapk]

[下载最新版本aar][lastaar]

[下载最新版本jar][lastjar]

Download or grab via Maven:

	<dependency>
	  <groupId>com.dou361.update</groupId>
	  <artifactId>jjdxm-update</artifactId>
	  <version>x.x.x</version>
	</dependency>

or Gradle:

	compile 'com.dou361.update:jjdxm-update:x.x.x'


jjdxm-update requires at minimum Java 15 or Android 4.0.

## Get Started ##

### SDK集成 ###
在项目中添加依赖(x.x.x为对应的版本号，如1.0.0)

### Android Studio集成 ###

在线快速集成

	compile 'com.dou361.update:jjdxm-update:x.x.x@aar'

离线集成复制aar文件到libs添加引用

	compile(name: 'jjdxm-update-x.x.x', ext: 'aar')

需要修改布局界面可集成，并复制release文件夹下的res-x.x.x到对应工程的资源文件夹中

	compile 'com.dou361.update:jjdxm-update:x.x.x'

### Eclipse集成 ###

复制release文件夹下面的压缩包jjdxm-update-x.x.x.jar放入工程的 libs 中和res-x.x.x文件夹放入工程对应的资源文件夹中，并添加引用
	
### 需要依赖v4包 ###

当前jar依赖v4 jar，需要在gradle文件中配置

    compile 'com.android.support:support-v4:x.x.x'

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

在Application的oncreate方法中调用
    
	init(this);

配置接口和解析数据

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

2016.06.20修复通知栏提示报错问题，修改v7.jar依赖方式，让用户自己去配置版本。下一个版本会接入在线参数和强制更新功能

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
[lastaar]:https://raw.githubusercontent.com/jjdxmashl/jjdxm_update/master/release/jjdxm-update-1.0.0.aar
[lastjar]:https://raw.githubusercontent.com/jjdxmashl/jjdxm_update/master/release/jjdxm-update-1.0.0.jar
[icon01]:https://raw.githubusercontent.com/jjdxmashl/jjdxm_update/master/screenshots/icon01.png
[icon02]:https://raw.githubusercontent.com/jjdxmashl/jjdxm_update/master/screenshots/icon02.png