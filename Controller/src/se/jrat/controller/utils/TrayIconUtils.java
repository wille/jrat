package se.jrat.controller.utils;

import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import se.jrat.controller.Main;
import se.jrat.controller.settings.Settings;
import se.jrat.controller.ui.frames.Frame;


public class TrayIconUtils {

	public static boolean useTray = false;
	private static TrayIcon icon;

	public static void initialize() {
		try {
			if (!SystemTray.isSupported()) {
				throw new Exception("Tray icon not supported");
			}
			if (!Settings.getGlobal().getBoolean("traynote")) {
				return;
			}

			MouseListener mouseListener = new MouseListener() {
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

				public void mouseEntered(MouseEvent arg0) {
				}

				public void mouseExited(MouseEvent arg0) {
				}

				public void mousePressed(MouseEvent arg0) {
				}

				public void mouseReleased(MouseEvent arg0) {
				}

			};

			SystemTray tray = SystemTray.getSystemTray();
			icon = new TrayIcon(IconUtils.getIcon("icon", true).getImage(), Main.instance.getTitle(), null);
			icon.addMouseListener(mouseListener);
			tray.add(icon);
			useTray = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void showMessage(String title, String message) {
		showMessage(title, message, TrayIcon.MessageType.INFO);
	}

	public static void showMessage(String title, String message, TrayIcon.MessageType type) {
		if (useTray) {
			icon.displayMessage(title, message, type);
		}
	}

	public static void setTitle(String s) {
		if (useTray) {
			icon.setToolTip(s);
		}
	}

}
