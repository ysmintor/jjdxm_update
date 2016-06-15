
# [jjdxmupdate][project] #
## Introduction ##
应用内更新，实现类似友盟自动更新sdk的更新模式，用户使用前只需要配置自己的服务器更新检查接口即可（必须接口），也可以拓展加入一个接口作为在线参数配置来实现（可选接口）可实现以下三种方式更新：
### 1.自动检测更新、手动检测更新 ###
### 2.静默更新（WiFi环境自动下载） ###
### 3.强制更新（配合在线参数使得当前版本无法使用） ###


[apk下载][downapk]
## Features ##
### 1.更新数据接口用户可自定义 ###
### 2.在线参数接口用户可自定义 ###
### 3.参数结构返回回到让用户自定义解析 ###
## Screenshots ##

<img src="https://raw.githubusercontent.com/jjdxmashl/jjdxmupdate/master/screenshots/icon01.png" width="300"> 
<img src="https://raw.githubusercontent.com/jjdxmashl/jjdxmupdate/master/screenshots/icon02.png" width="300"> 
<img src="https://raw.githubusercontent.com/jjdxmashl/jjdxmupdate/master/screenshots/icon03.png" width="300">

## Get Started ##

### 需要权限 ###

	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

### 1.在Application中配置，初始化配置接口和解析参数 ###


        UpdateConfig.init(this);

### 2.在mainActivity中调用 ###

	UpdateBuilder.create()
                .strategy(new UpdateStrategy() {
                    @Override
                    public boolean isShowUpdateDialog(Update update) {
                        return true;
                    }

                    @Override
                    public boolean isAutoInstall() {
                        return false;
                    }

                    @Override
                    public boolean isShowInstallDialog() {
                        return true;
                    }

                    @Override
                    public boolean isShowDownloadDialog() {
                        return true;
                    }
                })
                .check(MainActivity.this);

## More Actions ##

## ChangeLog ##

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
[project]:https://github.com/jjdxmashl/jjdxmupdate/
[issues]:https://github.com/jjdxmashl/jjdxmupdate/issues/new
[downapk]:https://raw.githubusercontent.com/jjdxmashl/jjdxmupdate/master/apk/app-debug.apk
[icon01]:https://raw.githubusercontent.com/jjdxmashl/jjdxmupdate/master/screenshots/icon01.png
[icon02]:https://raw.githubusercontent.com/jjdxmashl/jjdxmupdate/master/screenshots/icon02.png