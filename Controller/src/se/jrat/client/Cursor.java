package se.jrat.client;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import se.jrat.client.utils.IconUtils;

import com.redpois0n.oslib.OperatingSystem;


public class Cursor {
	
	public static final Map<OperatingSystem, ImageIcon> CURSORS = new HashMap<OperatingSystem, ImageIcon>();
	
	static {
		CURSORS.put(OperatingSystem.WINDOWS, IconUtils.getIcon("cursor-windows"));
		CURSORS.put(OperatingSystem.OSX, IconUtils.getIcon("cursor-osx"));
		CURSORS.put(null, IconUtils.getIcon("cursor"));
	}
	
	public static void drawCursor(OperatingSystem operatingsystem, Graphics2D g, int x, int y) {
		if (operatingsystem != OperatingSystem.WINDOWS || operatingsystem != OperatingSystem.OSX) {
			operatingsystem = null;
		}
		
		drawCursor(CURSORS.get(operatingsystem).getImage(), g, x, y);
	}
	
	public static void drawCursor(Image cursor, Graphics2D g, int x, int y) {
		g.drawImage(cursor, x, y, null);
	}

}
