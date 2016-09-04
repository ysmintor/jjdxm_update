
# [jjdxm_update][project] #

### Copyright notice ###

我在网上写的文章、项目都可以转载，但请注明出处，这是我唯一的要求。当然纯我个人原创的成果被转载了，不注明出处也是没有关系的，但是由我转载或者借鉴了别人的成果的请注明他人的出处，算是对前辈们的一种尊重吧！

虽然我支持写禁止转载的作者，这是他们的成果，他们有这个权利，但我不觉得强行扭转用户习惯会有一个很好的结果。纯属个人的观点，没有特别的意思。可能我是一个版权意识很差的人吧，所以以前用了前辈们的文章、项目有很多都没有注明出处，实在是抱歉！有想起或看到的我都会逐一补回去。

从一开始，就没指望从我写的文章、项目上获得什么回报，一方面是为了自己以后能够快速的回忆起曾经做过的事情，避免重复造轮子做无意义的事，另一方面是为了锻炼下写文档、文字组织的能力和经验。如果在方便自己的同时，对你们也有很大帮助，自然是求之不得的事了。要是有人转载或使用了我的东西觉得有帮助想要打赏给我，多少都行哈，心里却很开心，被人认可总归是件令人愉悦的事情。

站在了前辈们的肩膀上，才能走得更远视野更广。前辈们写的文章、项目给我带来了很多知识和帮助，我没有理由不去努力，没有理由不让自己成长的更好。写出好的东西于人于己都是好的，但是由于本人自身视野和能力水平有限，错误或者不好的望多多指点交流。

项目中如有不同程度的参考借鉴前辈们的文章、项目会在下面注明出处的，纯属为了个人以后开发工作或者文档能力的方便。如有侵犯到您的合法权益，对您造成了困惑，请联系协商解决，望多多谅解哈！若您也有共同的兴趣交流技术上的问题加入交流群QQ： 548545202

感谢作者[shelwee][author]，[yjfnypeu][author1]，本项目是基于[UpdateHelper][url]和[UpdatePlugin][url1]项目

## Introduction ##

应用内更新，实现类似友盟自动更新sdk的更新模式，用户使用前只需要配置自己的服务器更新检查接口即可（必须接口），也可以拓展加入一个接口作为在线参数配置来实现（可选接口）可实现以下四种种方式更新和是否强制更新组合使用，支持get、post方式请求网络，默认是使用get方式

## 更新检查 ##
### 1.手动更新：手动检测更新（所有网络类型环境检测并提示主要用于点击检测使用）  ###
### 2.自动更新：自动检测更新（所有网络类型环境检测并提示） ###
### 3.仅WiFi自动检测更新（只有WiFi网络类型环境检测并提示） ###
### 4.静默更新：仅WiFi自动检测下载（只有WiFi网络类型环境检测、下载完才提示） ###
## 强制更新 ##
两种方式，一是在更新检测返回后，直接设置update.setForce(true);二是配合在线参数使用，通过在线参数返回设置UpdateHelper.getInstance().setForced(true);结合以上几种方式组合使用，主要使用场景是当上一个版本的APP有重大bug或漏洞时，修改在线参数统一控制所有的APP用户，使得之前的所有版本必须要升级才能正常使用。主要原理：服务器上修改参数的数值，APP端获取后进行判断，如果为强制更新，则在打开应用是提示有新版APP更新，更新完成才能使用，提示框不消失，用户如果选择不更新则退出应用。


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

	compile 'com.dou361.update:jjdxm-update:1.0.4'
	compile 'com.dou361.update:jjdxm-update:1.0.3'
	compile 'com.dou361.update:jjdxm-update:1.0.2'
	compile 'com.dou361.update:jjdxm-update:1.0.1'
	compile 'com.dou361.update:jjdxm-update:1.0.0'


jjdxm-update requires at minimum Java 9 or Android 2.3.

[架包的打包引用以及冲突解决][jaraar]

## Proguard ##

根据你的混淆器配置和使用，您可能需要在你的proguard文件内配置以下内容：

	后续加上

[AndroidStudio代码混淆注意的问题][minify]

## Get Started ##
### step1 ###
#### 在项目主程序build.gradle文件中加入以下代码，即可引入当前更新版本库： ####

	compile 'com.dou361.update:jjdxm-update:1.0.4'

### step2 ###
#### 配置更新接口参数信息，初始化配置接口和解析参数 ####


这里必须配置一个在线更新接口及其的数据返回结构的解析，可选的是在线参数接口及其数据返回结构的解析，在线参数可以随机定义零个或多个不同意义的参数来达到在线修改apk的部分特性。

（1）创建一个自动更新的配置文件

    public class UpdateConfig {

        private static String checkUrl = "你的更新接口";
	    private static String onlineUrl = "你的在线参数接口";
		//临时使用的下载地址
	    private static String apkFile = "http://wap.apk.anzhi.com/data3/apk/201512/20/55089e385f6e9f350e6455f995ca3452_26503500.apk";

		public static void init(Context context) {
	        UpdateHelper.init(context);
	        UpdateHelper.getInstance()
					// 可填：请求方式,默认为get请求
                	.setMethod(RequestType.get)
	                // 必填：数据更新接口，方法有重载带参数的setCheckUrl(checkUrl, params)
	                .setCheckUrl(checkUrl)
	                // 可填：在线参数接口，方法有重载带参数的setOnlineUrl(onlineUrl, params)
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
	                        update.setForce(false);
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

（2）setCheckJsonParser方法回调的json字符串解析方法：

例如返回的json字符串为：

	{
	    "code": 0,
	    "data": {
	        "download_url": "http://115.159.45.251/software/feibei_live1.0.0.16070810_zs.apk ",
	        "force": false,
	        "update_content": "测试更新接口",
	        "v_code": "10",
	        "v_name": "v1.0.0.16070810",
	        "v_sha1": "7db76e18ac92bb29ff0ef012abfe178a78477534",
	        "v_size": 12365909
	    }
	}

使用第三方json工具解析：


    compile 'com.alibaba:fastjson:1.2.14'

	public class UpdateBean {
	    /**
	     * code : 0
	     * data : {"download_url":"http://115.159.45.251/software/feibei_live1.0.0.16070810_zs.apk ","force":false,"update_content":"测试更新接口","v_code":"10","v_name":"v1.0.0.16070810","v_sha1":"7db76e18ac92bb29ff0ef012abfe178a78477534","v_size":12365909}
	     */
	
	    private int code;
	    /**
	     * download_url : http://115.159.45.251/software/feibei_live1.0.0.16070810_zs.apk
	     * force : false
	     * update_content : 测试更新接口
	     * v_code : 10
	     * v_name : v1.0.0.16070810
	     * v_sha1 : 7db76e18ac92bb29ff0ef012abfe178a78477534
	     * v_size : 12365909
	     */
	
	    private DataBean data;
	
	    public int getCode() {
	        return code;
	    }
	
	    public void setCode(int code) {
	        this.code = code;
	    }
	
	    public DataBean getData() {
	        return data;
	    }
	
	    public void setData(DataBean data) {
	        this.data = data;
	    }
	
	    public static class DataBean {
	        private String download_url;
	        private boolean force;
	        private String update_content;
	        private String v_code;
	        private String v_name;
	        private String v_sha1;
	        private int v_size;
	
	        public String getDownload_url() {
	            return download_url;
	        }
	
	        public void setDownload_url(String download_url) {
	            this.download_url = download_url;
	        }
	
	        public boolean isForce() {
	            return force;
	        }
	
	        public void setForce(boolean force) {
	            this.force = force;
	        }
	
	        public String getUpdate_content() {
	            return update_content;
	        }
	
	        public void setUpdate_content(String update_content) {
	            this.update_content = update_content;
	        }
	
	        public String getV_code() {
	            return v_code;
	        }
	
	        public void setV_code(String v_code) {
	            this.v_code = v_code;
	        }
	
	        public String getV_name() {
	            return v_name;
	        }
	
	        public void setV_name(String v_name) {
	            this.v_name = v_name;
	        }
	
	        public String getV_sha1() {
	            return v_sha1;
	        }
	
	        public void setV_sha1(String v_sha1) {
	            this.v_sha1 = v_sha1;
	        }
	
	        public int getV_size() {
	            return v_size;
	        }
	
	        public void setV_size(int v_size) {
	            this.v_size = v_size;
	        }
	    }
	}

	UpdateBean mUpdateBean = JSON.parseObject(json, UpdateBean.class);


使用系统的json对象解析：

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
            if (!job.isNull("force")) {
                // 此apk包的下载地址
                update.setForce(job.optBoolean("force", false));
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

### step3 ###
#### 在Application中oncreate()方法中调用 ####

	UpdateConfig.init(this);

### step4 ###
(1)在mainActivity中oncreate()方法中调用

	//默认是自动检测更新
	UpdateHelper.getInstance()
                .check(MainActivity.this);

(2)在需要手动点击的方法中调用

	//手动检测更新
	UpdateHelper.getInstance()
                .setUpdateType(UpdateType.checkupdate)
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

### 1.几种方式的调用 ###

	//有网更新
	UpdateType.checkupdate,
	//自动更新
    UpdateType.autoupdate,
	//只有WiFi更新
    UpdateType.autowifiupdate,
	//只有WiFi下载
    UpdateType.autowifidown

调用方式这里只举例静默更新，其他方式类似

	/静默更新
	UpdateHelper.getInstance()
				.setUpdateType(UpdateType.autowifidown)
                .check(MainActivity.this);

### 2.更新监听回调UpdateListener，主要有四个方法 ###

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

### 自动更新接口和在线参数接口调用的顺序 ###
checkUpdate和checkOnline的接口调用的有先后顺序之分，遇到相同的配置时后调用的会覆盖先调用的，例如设置强制更新的配置

	UpdateHelper.getInstance().setForced(true);

### 强制更新，需要设置回调监听setForceListener ###

	UpdateHelper.getInstance().setForceListener(new ForceListener() {
                    @Override
                    public void onUserCancel(boolean force) {
                        if (force) {
                            //用户点击取消操作后的处理，一般是退出应用
                            finish();
                        }
                    }
                });

### 在线参数接口的使用场景 ###
在线参数接口，可以用来做一个在线动态配置应用的是否强制更新属性，当然也可以直接在自动更新接口中做强制更新的操作。因此，在线参数接口更多的功能是为了提供给用户配置一些在线的参数配置。

### 自定义弹出框样式 ###
通过setDialogLayout(R.layout.custom_update_dialog)方法实现自定义布局，可以复制以下案例布局，重新命名一个名称例如：custom_update_dialog

	<?xml version="1.0" encoding="utf-8"?>
	<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
	                android:layout_width="match_parent"
	                android:layout_height="match_parent"
	                android:background="#99232323">
	
	    <LinearLayout
	        android:layout_width="280dp"
	        android:layout_height="wrap_content"
	        android:layout_centerInParent="true"
	        android:background="@drawable/jjdxm_update_dialog_bg"
	        android:orientation="vertical"
	        android:paddingBottom="8dp">
	
	        <!-- Title -->
	
	        <RelativeLayout
	            android:layout_width="fill_parent"
	            android:layout_height="40dp">
	
	            <ImageView
	                android:id="@+id/jjdxm_update_wifi_indicator"
	                android:layout_width="30dp"
	                android:layout_height="30dp"
	                android:layout_centerVertical="true"
	                android:layout_marginLeft="10dp"
	                android:contentDescription="@string/jjdxm_update_gprscondition"
	                android:src="@drawable/jjdxm_update_wifi_disable"/>
	
	            <TextView
	                android:layout_width="wrap_content"
	                android:layout_height="wrap_content"
	                android:layout_centerInParent="true"
	                android:text="@string/jjdxm_update_updatetitle"
	                android:textAppearance="?android:attr/textAppearanceLarge"
	                android:textColor="#008bea"/>
	
	        </RelativeLayout>
	
	        <!-- split -->
	
	        <View
	            android:layout_width="fill_parent"
	            android:layout_height="2dp"
	            android:background="#008bea"/>
	        <!-- Content -->
	
	        <ScrollView
	            android:layout_width="fill_parent"
	            android:layout_height="0dp"
	            android:layout_weight="1"
	            android:padding="10dp">
	
	            <LinearLayout
	                android:layout_width="fill_parent"
	                android:layout_height="wrap_content"
	                android:orientation="vertical">
	
	                <TextView
	                    android:id="@+id/jjdxm_update_content"
	                    android:layout_width="fill_parent"
	                    android:layout_height="wrap_content"
	                    android:layout_marginLeft="5dp"
	                    android:layout_marginRight="5dp"
	                    android:layout_marginTop="10dp"
	                    android:focusable="true"
	                    android:textColor="#000"/>
	            </LinearLayout>
	        </ScrollView>
	
	        <!-- Ignore CheckBox -->
	
	        <CheckBox
	            android:id="@+id/jjdxm_update_id_check"
	            android:layout_width="fill_parent"
	            android:layout_height="32dp"
	            android:button="@drawable/jjdxm_update_button_check_selector"
	            android:text="@string/jjdxm_update_ignore"
	            android:textColor="#000"/>
	
	        <!-- OK&Cancel Button -->
	
	        <LinearLayout
	            android:layout_width="fill_parent"
	            android:layout_height="wrap_content">
	
	            <Button
	                android:id="@+id/jjdxm_update_id_ok"
	                android:layout_width="0dp"
	                android:layout_height="wrap_content"
	                android:layout_margin="5dp"
	                android:layout_weight="1"
	                android:background="@drawable/jjdxm_update_button_ok_bg_selector"
	                android:focusable="true"
	                android:gravity="center"
	                android:padding="12dp"
	                android:text="@string/jjdxm_update_updatenow"
	                android:textColor="#FFFFFF"/>
	
	            <Button
	                android:id="@+id/jjdxm_update_id_cancel"
	                android:layout_width="0dp"
	                android:layout_height="wrap_content"
	                android:layout_margin="5dp"
	                android:layout_weight="1"
	                android:background="@drawable/jjdxm_update_button_cancel_bg_selector"
	                android:focusable="true"
	                android:gravity="center"
	                android:padding="12dp"
	                android:text="@string/jjdxm_update_notnow"
	                android:textColor="#000"/>
	        </LinearLayout>
	    </LinearLayout>
	
	</RelativeLayout>

以上案例中的布局样式里面的除了id为（jjdxm_update_content、jjdxm_update_id_ok、jjdxm_update_id_cancel）的view类型和id不能修改，其他的都可以修改或删除。
                

## ChangeLog ##

2016.09.04 修复只能通过post方式请求接口。添加在线参数和强制更新功能，添加自定义弹出布局的样式

2016.07.29 修复07.28打包post请求方式造成的get请求需要传params集合问题。
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
[author1]:https://github.com/yjfnypeu
[url1]:https://github.com/yjfnypeu/UpdatePlugin
