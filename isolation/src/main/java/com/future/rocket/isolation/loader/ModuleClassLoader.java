package com.future.rocket.isolation.loader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ModuleClassLoader extends URLClassLoader {
    //类加载器根据jarFile生成不同的加载器实例
    //由类加载器和类的完全限定名来共同确定一个类
    public ModuleClassLoader(File jarFile) throws MalformedURLException {
        super(new URL[]{jarFile.toURI().toURL()});
    }
}
