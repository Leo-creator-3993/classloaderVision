package com.future.rocket.classloader.factory;

import com.future.rocket.classloader.custom.EnhancingClassLoader;

public class InstanceFactory {
    private static EnhancingClassLoader enhancingClassLoader = new EnhancingClassLoader(ClassLoader.getSystemClassLoader());

    public static <T> T createInstance(Class<T> clazz) throws Exception {
        // 强制使用 EnhancingClassLoader 加载类
        Class<?> enhancingClazz = enhancingClassLoader.loadClass(clazz.getName(), false); // 加载类但不初始化
        return (T) enhancingClazz.getDeclaredConstructor().newInstance();
    }
}
