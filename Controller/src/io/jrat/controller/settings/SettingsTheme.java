package io.jrat.controller.settings;

import io.jrat.controller.Globals;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import javax.swing.UIManager;


public class SettingsTheme extends AbstractStorable {

	private static final SettingsTheme instance = new SettingsTheme();

	private String theme;

	public static SettingsTheme getGlobal() {
		return instance;
	}

	@Override
	public void save() throws Exception {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getFile())));

		String[] themeargs = UIManager.getLookAndFeel().toString().split(" - ");
		String className = themeargs[1].substring(0, themeargs[1].length() - 1);

		writer.write(className);
		writer.newLine();
		writer.close();
	}

	@Override
	public void load() throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(getFile())));
		this.theme = reader.readLine();
		reader.close();
	}

	@Override
	public File getFile() {
		return new File(Globals.getSettingsDirectory(), ".theme");
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

}
