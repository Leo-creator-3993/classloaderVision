package com.future.rocket.classloader.custom;

import javassist.ByteArrayClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class EnhancingClassLoader extends ClassLoader {

    public EnhancingClassLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        // 判断是否是我们需要增强的类，避免加载非目标类
        if (name.startsWith("com.future.rocket.classloader.service")) {
            return findClass(name);  // 强制调用 findClass
        }
        return super.loadClass(name, resolve);  // 对于其他类使用默认加载
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            String path = name.replace('.', '/') + ".class"; // 移除前面的 "/"
            System.out.println("==> Class path for loading ==> 加载类的路径 ==> " + path);  // 输出路径，便于调试
            InputStream input = getParent().getResourceAsStream(path);
            if (input == null) {
                throw new ClassNotFoundException(name + " not found at " + path);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            byte[] classBytes = outputStream.toByteArray();

            // 使用 Javassist 修改字节码
            byte[] enhancedBytes = enhanceClass(classBytes, name);

            return defineClass(name, enhancedBytes, 0, enhancedBytes.length);
        } catch (Exception e) {
            throw new ClassNotFoundException("Could not load class " + name, e);
        }
    }

    private byte[] enhanceClass(byte[] classBytes, String className) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath(new ByteArrayClassPath(className, classBytes)); // 插入字节码路径
        CtClass ctClass = pool.get(className);
        ctClass.defrost(); // 重置类，防止冻结

        // 在构造函数中插入增强逻辑
        for (CtConstructor constructor : ctClass.getDeclaredConstructors()) {
            constructor.insertBefore("System.out.println(\"##### 动态增强 ==>[Enhanced By Foo] 前置调用 ==> Before Instance created ==> " + className + "\");");
        }

        // 返回增强后的字节码
        byte[] enhancedBytes = ctClass.toBytecode();
        ctClass.detach(); // 从缓存中移除，确保不会重复使用

        return enhancedBytes;
    }
}
