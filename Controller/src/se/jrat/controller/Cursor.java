package se.jrat.controller;

import java.awt.Graphics2D;
import java.awt.Image;

import se.jrat.controller.utils.IconUtils;

import com.redpois0n.oslib.OperatingSystem;

public class Cursor {

	public static void drawCursor(OperatingSystem operatingsystem, Graphics2D g, int x, int y) {
		String icon;

		if (operatingsystem == OperatingSystem.OSX) {
			icon = "cursor-osx";
		} else {
			icon = "cursor-default";
		}

		drawCursor(IconUtils.getIcon(icon).getImage(), g, x, y);
	}

	public static void drawCursor(Image cursor, Graphics2D g, int x, int y) {
		g.drawImage(cursor, x, y, null);
	}

}
