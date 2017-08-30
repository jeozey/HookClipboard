package com.rj.testhook;

import android.content.ClipData;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/8/25.
 */

public class HookBinderInvocationHandler implements InvocationHandler {
    private final static String TAG = HookBinderInvocationHandler.class.getSimpleName();
    private Object base;

    public HookBinderInvocationHandler(IBinder base, Class<?> stubClass) {
        try {
            Method asInterfaceMethod = stubClass.getDeclaredMethod("asInterface", IBinder.class);
            this.base = asInterfaceMethod.invoke(null, base);
        } catch (Exception e) {
            throw new RuntimeException("hooked failed");
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.e(TAG, "invoke: " + method.getName());
        if ("getPrimaryClip".equals(method.getName())) {
            return ClipData.newPlainText(null, "you are hooked");
        } else if ("hasPrimaryClip".equals(method.getName())) {
            return true;
        }
        return method.invoke(base, args);
    }
}
