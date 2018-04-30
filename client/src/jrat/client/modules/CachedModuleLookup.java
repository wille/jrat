package jrat.client.modules;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;


public class CachedModuleLookup {

    public static byte[] lookupSum(String mainClass) throws Exception {
        File file = CachedModule.getFile(mainClass);

        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        JarInputStream jis = new JarInputStream(new FileInputStream(file));

        JarEntry entry = null;
        while ((entry = jis.getNextJarEntry()) != null) {
            byte[] buffer = new byte[1024];

            int r;
            while ((r = jis.read(buffer)) != -1) {
                digest.update(buffer, 0, r);
            }
        }

        return digest.digest();
    }
}
