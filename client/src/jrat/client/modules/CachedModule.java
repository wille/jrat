package jrat.client.modules;

import java.io.File;
import java.io.FileOutputStream;

public class CachedModule {

    public static File getFile(String mainClass) {
        String tmp = System.getProperty("java.io.tmpdir");
        return new File(tmp, mainClass);
    }

    public static void store(String mainClass, byte[] buffer) throws Exception {
        FileOutputStream fos = new FileOutputStream(getFile(mainClass));
        fos.write(buffer);
        fos.close();
    }
}
