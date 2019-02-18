package com.asproject.module;

import android.widget.Toast;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class CallModule extends ReactContextBaseJavaModule {
    private final ReactApplicationContext mContext;

    public CallModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mContext = reactContext;
    }

    @Override
    public String getName() {
        return "CallModule";
    }

    @ReactMethod
    public void callTost(String text){
        Toast.makeText(getReactApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
    @ReactMethod
    public void callTost(String text, Callback callBack){
        Toast.makeText(getReactApplicationContext(), text, Toast.LENGTH_SHORT).show();
        callBack.invoke("success");
    }


}
