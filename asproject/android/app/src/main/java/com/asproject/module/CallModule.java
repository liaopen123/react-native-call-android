package com.asproject.module;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.HashMap;
import java.util.Map;

public class CallModule extends ReactContextBaseJavaModule {
    private static final String LONG_TIME = "LONG";
    private static final String SHORT_TIME = "SHORT";
    private static final String MESSAGE = "MESSAGE";
    private final ReactApplicationContext mContext;

    public CallModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mContext = reactContext;
    }

    @Override
    public String getName() {
        return "CallModule";
    }

    @Override
    public Map<String, Object> getConstants() {
        //让js那边能够使用这些常量
        Map<String,Object> constants = new HashMap<>();
        constants.put(LONG_TIME, Toast.LENGTH_LONG);
        constants.put(SHORT_TIME,Toast.LENGTH_SHORT);
        constants.put(MESSAGE,"getConstants");
        return constants;
    }
    
    
    @ReactMethod
    public void callTost(String text){
        Toast.makeText(getReactApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
    //方式1 通过callback进行回调
    @ReactMethod
    public void callTost(String text, Callback callBack){
        Toast.makeText(getReactApplicationContext(), text, Toast.LENGTH_SHORT).show();
        callBack.invoke("success");
    }

    //方式2 通过promise进行回调
    @ReactMethod
    public void callToast1(String text, Promise promise){
        Toast.makeText(getReactApplicationContext(), text, Toast.LENGTH_SHORT).show();
        promise.resolve("success");
    }

    @ReactMethod
    public void getDataFromIntent(Callback callback){
        try{
            Activity currentActivity=getCurrentActivity();
            String result =currentActivity.getIntent().getStringExtra("data");
            if(TextUtils.isEmpty(result)){
                callback.invoke("no_datalph11111111111111");
            }else{
                callback.invoke(result);
            }
        }catch (Exception e){
            callback.invoke("errorlph");
        }
    }

    @ReactMethod
    public void sendEvent(){
        onScanningResult();
    }

    @ReactMethod
    public void textAndroidPromiseMethod(String msg, Promise promise){
        Toast.makeText(getReactApplicationContext(),msg,Toast.LENGTH_SHORT).show();
        String result="lph23333";
        promise.resolve(result);
    }

    @ReactMethod
    public void testAndroidCallbackMethod(String msg, Callback callback){
        Toast.makeText(getReactApplicationContext(),msg,Toast.LENGTH_LONG).show();
        callback.invoke("abc");
    }
    @ReactMethod
    public void show(int duration){
        Toast.makeText(getReactApplicationContext(),"message:"+duration,duration).show();
    }

    public void onScanningResult(){
        WritableMap params = Arguments.createMap();
        params.putString("key", "myData");
        sendEvent(getReactApplicationContext(),"EventName",params);
    }

    public static void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }

}
