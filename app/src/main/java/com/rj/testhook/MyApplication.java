package com.rj.testhook;

import android.app.Application;
import android.content.Context;
import android.os.IBinder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/30.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        hook();
    }
    private void hook(){
        try {
            Class<?> serviceManager = Class.forName("android.os.ServiceManager");
            Method getService = serviceManager.getDeclaredMethod("getService",String.class);
            IBinder rawBinder = (IBinder) getService.invoke(null, Context.CLIPBOARD_SERVICE);

            IBinder hookedBinder = (IBinder) Proxy.newProxyInstance(serviceManager.getClassLoader(),new Class<?>[]{IBinder.class},
            new IClipboardHookBinderHandler(rawBinder));

            Field cacheField = serviceManager.getDeclaredField("sCache");
            cacheField.setAccessible(true);
            Map<String,IBinder> cache = (Map<String, IBinder>) cacheField.get(null);
            cache.put(Context.CLIPBOARD_SERVICE,hookedBinder);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
