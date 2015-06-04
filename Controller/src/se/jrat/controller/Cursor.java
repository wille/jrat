package se.jrat.controller;

import iconlib.IconUtils;

import java.awt.Graphics2D;

import com.redpois0n.oslib.OperatingSystem;

public class Cursor {

	/**
	 * Draw cursor
	 * @param os gets matching icon for operating system
	 * @param g Graphics to paint the cursor on
	 * @param x coordinate
	 * @param y coordinate
	 */
	public static void drawCursor(OperatingSystem os, Graphics2D g, int x, int y) {
		String icon;

		if (os == OperatingSystem.OSX) {
			icon = "cursor-osx";
		} else {
			icon = "cursor-default";
		}

		g.drawImage(IconUtils.getIcon(icon).getImage(), x, y, null);
	}
}
