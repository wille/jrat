package se.jrat.controller.utils;

import iconlib.IconUtils;

import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import se.jrat.controller.Main;
import se.jrat.controller.settings.Settings;
import se.jrat.controller.ui.frames.Frame;


public class TrayIconUtils {

	public static boolean usingTray = false;
	private static TrayIcon icon;

	public static void initialize() {
		try {
			if (!SystemTray.isSupported()) {
				throw new Exception("Tray icon not supported");
			}
			if (!Settings.getGlobal().getBoolean("traynote")) {
				return;
			}

			SystemTray tray = SystemTray.getSystemTray();
			icon = new TrayIcon(IconUtils.getIcon("icon-16x16").getImage(), Main.instance.getTitle(), null);
			icon.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2 && !e.isConsumed()) {
						e.consume();
						Frame frame = Main.instance;
						if (frame.getState() == Frame.ICONIFIED) {
							frame.setState(Frame.NORMAL);
						} else {
							frame.setState(Frame.ICONIFIED);
						}
					}
				}
			});
			tray.add(icon);
			usingTray = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void showMessage(String title, String message) {
		showMessage(title, message, TrayIcon.MessageType.INFO);
	}

	public static void showMessage(String title, String message, TrayIcon.MessageType type) {
		if (usingTray) {
			icon.displayMessage(title, message, type);
		}
	}

	public static void setTitle(String s) {
		if (usingTray) {
			icon.setToolTip(s);
		}
	}

}
