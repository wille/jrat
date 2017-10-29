package jrat.api;

import jrat.client.MemoryClassLoader;

import javax.swing.*;
import java.io.InputStream;

public class Resources {

    /**
     * Gets an icon from memory
     * @param name of the icon without extension
     * @return
     */
    public static ImageIcon getIcon(String name) {
        name = name + ".png";

        byte[] data = MemoryClassLoader.getResourceData(name);

        ImageIcon icon = null;

        if (data != null) {
            icon = new ImageIcon(data);
        }

        return icon;
    }

    /**
     * Returns an input stream for the resource in memory
     * @param name
     * @return
     */
    public static InputStream getInputStream(String name) {
        return MemoryClassLoader.getResourceStream(name);
    }
}
