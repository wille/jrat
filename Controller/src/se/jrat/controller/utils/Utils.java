package se.jrat.controller.utils;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import se.jrat.controller.AbstractSlave;
import se.jrat.controller.Main;
import se.jrat.controller.ui.components.TableModel;

public class Utils {

	public static AbstractSlave getSlave(String ip) {
		for (int i = 0; i < Main.connections.size(); i++) {
			if (Main.connections.get(i).getIP().equals(ip)) {
				return Main.connections.get(i);
			}
		}
		return null;
	}

	public static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	public static String showDialog(String title, String text) {
		return JOptionPane.showInputDialog(null, text, title, JOptionPane.QUESTION_MESSAGE);
	}

	public static boolean yesNo(String title, String message) {
		return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
	}

	public static String[] getFiles(int[] rows, TableModel model) {
		String[] str = new String[rows.length];
		for (int i = 0; i < rows.length; i++) {
			int row = rows[i];
			str[i] = model.getValueAt(row, 0).toString();
		}
		return str;
	}

	public static void center(JFrame window) {
		int widthWindow = window.getWidth();
		int heightWindow = window.getHeight();
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int X = (screen.width / 2) - (widthWindow / 2);
		int Y = (screen.height / 2) - (heightWindow / 2);

		window.setBounds(X, Y, widthWindow, heightWindow);
	}

	public static String getDate() {
		Date now = new Date();
		return now.toString();
	}

	public static File getWorkingDir() {
		return new File(System.getProperty("user.dir"));
	}

	public static String getDate(File file) {
		return getDate(file.lastModified());
	}
	
	public static String getDate(long l) {
		Date now = new Date(l);
		return now.toString();
	}

}
