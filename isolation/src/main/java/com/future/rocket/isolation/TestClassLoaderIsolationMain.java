package com.future.rocket.isolation;

import com.future.rocket.isolation.loader.ModuleClassLoader;



import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;


public class TestClassLoaderIsolationMain {

    public static void main(String[] args) throws Exception {
        String absoluteClassPath = "com.future.rocket.test.classloader.isolation.FooService";
        String methodName = "greet";
        String jarPath1 = "isolation/src/main/resources/libs/testClassLoaderIsolation-1.0-v1.jar";
        String jarPath2 = "isolation/src/main/resources/libs/testClassLoaderIsolation-1.0-v2.jar";
        Class<?> clazz1 = executeJarFile(jarPath1, absoluteClassPath, methodName);
        Class<?> clazz2 = executeJarFile(jarPath2, absoluteClassPath, methodName);

        System.out.println("is Class Equals ==> " + (clazz1 == clazz2));
    }

    private static  Class<?>  executeJarFile(String path, String absoluteClassPath, String methodName) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        File jarFile = new File(path);
        ModuleClassLoader moduleLoader = new ModuleClassLoader(jarFile);
        Class<?> loadClass = moduleLoader.loadClass(absoluteClassPath);
        Object instance = loadClass.getDeclaredConstructor().newInstance();
        Method method = loadClass.getMethod(methodName);
        method.invoke(instance);
        System.out.println("is Instance ==> " + loadClass.getName().equals("com.future.rocket.test.classloader.isolation.FooService"));
        return loadClass;
    }
}
