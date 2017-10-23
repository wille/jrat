package jrat.api;

import javax.swing.*;
import java.net.URL;

public class Resources {

    /**
     * Gets an icon resource
     * @param path
     * @return
     */
    public static ImageIcon getIcon(String path) {
        URL url = Resources.class.getResource("/" + path + ".png");

        if(url == null) {
            return null;
        }

        return new ImageIcon(url);
    }
}
