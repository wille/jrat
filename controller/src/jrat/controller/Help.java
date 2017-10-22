package jrat.controller;

import jrat.controller.utils.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import javax.swing.JOptionPane;

public class Help {

	/**
	 * Displays message dialog with title "Help" and question icon
	 * @param msg message to display
	 */
	public static void help(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Help", JOptionPane.QUESTION_MESSAGE);
	}

	/**
	 * Reads help doc from file
	 * @param help
	 * @return help document
	 */
	public static String getHelp(String help) {
		try {
			help = help.toLowerCase().trim();
			FileInputStream in = new FileInputStream(new File(Globals.getHelpDocDirectory(), help));
			return IOUtils.readString(in);
		} catch (Exception ex) {
			return null;
		}
	}

}
