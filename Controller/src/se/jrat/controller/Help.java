package se.jrat.controller;

import java.io.File;
import java.io.FileInputStream;

import javax.swing.JOptionPane;

import se.jrat.controller.utils.IOUtils;

public class Help {

	public static void help(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Help", JOptionPane.QUESTION_MESSAGE);
	}

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
