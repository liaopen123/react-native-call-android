---
layout:     post
title:  React Native调用Android原生方法和传值
subtitle: RN Call Android 和  传值
date:     2019-02-18
author:     Pony
header-img: img/post-bg-mayday-bubble.jpg
catalog: true
tags:
    - ReactNative
    - Android
---
## 创建react native 项目：
 > react-native init callAndroidProject
 >
 >cd callAndroidProject
 >
 >react-native run-android 
 
 这样就初步创建出了一个React-Natvie项目
 
## 完成Android端的操作
 
### 第一步： 创建一个ReactModule类 继承 `ReactContextBaseJavaModule`。
这个类是一个桥梁类，react-native调用Android就在这里进行操作。
复写其中的1个方法：`getName()`。**返回的名称用于在rn代码中调用时使用**。
如果要添加调用方法，需要在新增的方法中添加注解`@ReactMethod`.
具体代码实现：
```java
public class MyCallModule extends ReactContextBaseJavaModule {
    private final ReactApplicationContext mContext;

    public MyCallModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mContext = reactContext;
    }

    @Override
    public String getName() {
        return "MyCallModule";
    }


    @ReactMethod
    public void showToast(String str,Callback callback){
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
        callback.invoke("success");
    }
}
```
### 第二步：创建一个类 用于实现 `ReactPackage`。
主要用于注册module。当有多个module类的时候，用ReactPackage类，注册多个module。
代码展示：
```java
public class TotalPackage implements ReactPackage {
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> list = new ArrayList<>();
        list.add(new MyCallModule(reactContext));//在这添加 交互的module
        //list.add(...)
        return list;
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
```

### 第三步：在Application类中`ReactNativeHost#getPackages`添加步骤二中的package。
```java
public class MainApplication extends Application implements ReactApplication {

    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage(),
                    new TotalPackage()////新添加需要注册的模块
            );
        }

        @Override
        protected String getJSMainModuleName() {
            return "index";
        }
    };
    }
```
 这样 Android 原生端的代码就初步完成了。
 
## 完成React Native端操作
需要注意的地方有：
1. 添加的有Android端的交互，所以导包的时候需要import {NativeModules}。
2. `NativeModules.MyCallModule.showToast`中`MyCallModule`为Android端`MyCallModule#getName()`返回值。
3. `AppRegistry.registerComponent('callAndroidProject', () => HelloWorldApp);`中引号括起来的'callAndroidProject'必须和你init创建的项目名一致。


代码如下所示：
```javascript
import React, { Component } from 'react';
import { AppRegistry, Text,NativeModules} from 'react-native';//添加的有NativeModules交互，使用就需要导入NativeModules到项目中。

class HelloWorldApp extends Component {
  _toast(){
    NativeModules.MyCallModule.showToast('toastlph111',(success)=>{alert(success)})
}
  render() {
    return (
      <Text  onPress={this._toast} >Hello 22222222221!</Text>
    );
  }
}

// 注意，这里用引号括起来的'asproject'必须和你init创建的项目名一致
AppRegistry.registerComponent('callAndroidProject', () => HelloWorldApp);
```


这样就初步完成了React Native 与 Android端的交互流程。
