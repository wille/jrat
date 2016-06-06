package io.jrat.controller.utils;

import iconlib.IconUtils;
import io.jrat.controller.Main;
import io.jrat.controller.settings.Settings;
import io.jrat.controller.ui.frames.Frame;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class TrayIconUtils {

	public static boolean usingTray = false;
	private static TrayIcon icon;

	/**
	 * Initialize global tray icon
	 */
	public static void initialize() {
		if (!Main.headless) {
			try {
				if (!SystemTray.isSupported()) {
					throw new Exception("Tray icon not supported");
				}
				if (!Settings.getGlobal().getBoolean(Settings.KEY_USE_TRAY_ICON)) {
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
	}

	/**
	 * Shows information message in tray icon
	 * @param title
	 * @param message
	 */
	public static void showMessage(String title, String message) {
		showMessage(title, message, TrayIcon.MessageType.INFO);
	}

	/**
	 * Shows message in tray icon
	 * @param title Title of message
	 * @param message 
	 * @param type Message type
	 */
	public static void showMessage(String title, String message, TrayIcon.MessageType type) {
		if (usingTray) {
			icon.displayMessage(title, message, type);
		}
	}

	/**
	 * Sets tray icon tooltip
	 * @param tooltip
	 */
	public static void setToolTip(String tooltip) {
		if (usingTray) {
			icon.setToolTip(tooltip);
		}
	}

}
