package com.future.rocket.classloader.service;

import com.future.rocket.classloader.GreetAble;

public class Foo implements GreetAble {

    public Foo() {
        System.out.println("FooFoo");
    }

    @Override
    public void greet() {
        System.out.println("LeoLeo...");
    }
}
