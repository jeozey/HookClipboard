package com.rj.testhook;

import android.os.IBinder;
import android.os.IInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2017/8/30.
 */

public class IClipboardHookBinderHandler implements InvocationHandler {
    IBinder base;
    Class iinterface;
    Class stub;

    public IClipboardHookBinderHandler(IBinder rawBinder) {
        this.base = rawBinder;
        try {
            this.stub = Class.forName("android.content.IClipboard$Stub");
            this.iinterface = Class.forName("android.content.IClipboard");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("queryLocalInterface".equals(method.getName())) {
            return Proxy.newProxyInstance(base.getClass().getClassLoader(),
                    new Class<?>[]{this.iinterface}, new HookBinderInvocationHandler(base, stub));
        }
        return method.invoke(base, args);
    }
}
