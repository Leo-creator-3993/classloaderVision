package com.future.rocket.classloader;

import com.future.rocket.classloader.factory.InstanceFactory;
import com.future.rocket.classloader.service.Foo;

public class LaunchMain {

    public static void main(String[] args) throws Exception {
        load1();
    }

    //反射方式加载
    private static void load0() throws Exception {
        System.out.println("==> 实例增强测试启动...");
        Object foo = InstanceFactory.createInstance(Class.forName("com.future.rocket.classloader.service.Foo"));
        foo.getClass().getMethod("greet").invoke(foo);
        System.out.println("isInstance ==> " + (foo instanceof Foo));
    }

    //接口方式动态加载
    private static void load1() throws Exception{
        //通过接口动态加载
        System.out.println("==> 实例增强测试启动...");
        GreetAble foo = (GreetAble) InstanceFactory.createInstance(Class.forName("com.future.rocket.classloader.service.Foo"));
        foo.greet();
    }
}
