package se.jrat.client.utils;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;

import se.jrat.client.AbstractSlave;
import se.jrat.client.Main;
import se.jrat.client.ui.frames.Frame;

import com.redpois0n.oslib.Icons;


public class IconUtils {
	
	public static final Map<String, ImageIcon> CACHE = new HashMap<String, ImageIcon>();

	public static ImageIcon getIcon(String name, boolean defaultfolder) {
		URL url;
		if (defaultfolder) {
			url = Main.class.getResource("/icons/" + name + ".png");
			
			if (name.endsWith(".png")) {
				Main.debug("Image in default folder ends with .png: " + name);
			}
		} else {
			url = Main.class.getResource(name);		
		}

		if (CACHE.containsKey(name)) {
			return CACHE.get(name);
		} else {
			ImageIcon icon = new ImageIcon(url);
			CACHE.put(name, icon);
			return icon;
		}
	}

	public static ImageIcon getIcon(String name) {
		return getIcon(name, true);
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

		String str = null;

		if (Frame.pingmode == Frame.PING_ICON_DOT) {
			str = "ping" + ping;
		} else {
			if (ping == 0 || ping == 1) {
				str = "network-green";
			} else if (ping == 2 || ping == 3) {
				str = "network-yellow";
			} else if (ping == 4) {
				str = "network-red";
			} else {
				str = "network-offline";
			}
		}

		ImageIcon icon;

		if (CACHE.containsKey(str)) {
			icon = CACHE.get(str);
		} else if (Frame.pingmode == Frame.PING_ICON_DOT) {
			icon = IconUtils.getIcon(str);
			CACHE.put(str, icon);
		} else {
			icon = getIcon(str);
			CACHE.put(str, icon);
		}

		return icon;
	}
	
	public static ImageIcon getOSIcon(AbstractSlave slave) {
		String str = Icons.getIconString(slave.getOperatingSystem());
		ImageIcon icon = IconUtils.getIcon(str);
		if (icon == null) {
			icon = IconUtils.getIcon("os_unknown");
		}
		return icon;	
	}
	
	public static ImageIcon getFileIcon(int mode) {
		if (mode == IconUtils.IMAGE_FILE) {
			return IconUtils.getIcon("file");
		} else if (mode == IconUtils.IMAGE_FOLDER) {
			return IconUtils.getIcon("folder");
		} else {
			return null;
		}
	}

	public static Icon getFolderIcon() {
		return getFileIconFromExtension("", true);
	}

	public static Icon getFileIconFromExtension(String f, boolean dir) {
		if (dir) {
			try {
				File temp = new File(System.getProperty("java.io.tmpdir") + "icon");
				temp.mkdirs();
				Icon icon = FileSystemView.getFileSystemView().getSystemIcon(temp);
				temp.delete();
				return icon;
			} catch (Exception ex) {
				return getFileIcon(IconUtils.IMAGE_FOLDER);
			}
		} else {
			try {
				File temp = File.createTempFile((new Random()).nextInt() + "", f.substring(f.lastIndexOf("."), f.length()));
				Icon icon = FileSystemView.getFileSystemView().getSystemIcon(temp);
				temp.delete();
				return icon;
			} catch (Exception ex) {
				return getFileIcon(IconUtils.IMAGE_FILE);
			}
		}
	}

	public static Icon getFileIcon(File f) {
		Icon icon = FileSystemView.getFileSystemView().getSystemIcon(f);
		return icon;
	}

	public static Icon getFileIcon(String f) {
		return getFileIcon(new File(f));
	}

	public static final int IMAGE_FOLDER = 0;
	public static final int IMAGE_FILE = 1;

}
