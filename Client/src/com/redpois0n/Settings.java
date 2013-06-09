package com.redpois0n;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.redpois0n.io.Files;

public class Settings extends AbstractSettings {

	private static final Settings instance = new Settings();
	
	private Map<String, Object> settings = new HashMap<String, Object>();

	public static Settings getGlobal() {
		return instance;
	}
	
	public String getString(String key) {
		Object obj = settings.get(key);
		return obj == null ? "" : (String) obj;
	}
	
	public Object get(String key) {
		return settings.get(key);
	}

	public int getInt(String key) {
		Object obj = settings.get(key);
		return obj == null ? -1 : (Integer) obj;
	}

	public boolean getBoolean(String key) {
		Object obj = settings.get(key);
		return obj == null ? false : obj.toString().equalsIgnoreCase("true");
	}

	public void setVal(String key, Object value) {
		settings.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public void load() throws Exception {
		try {
			ObjectInputStream str = new ObjectInputStream(new FileInputStream(getFile()));
			settings = (HashMap<String, Object>) str.readObject();
			str.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (settings.size() == 0) {
			addDefault();
		}
	}

	public void save() throws Exception {
		ObjectOutputStream str = new ObjectOutputStream(new FileOutputStream(getFile()));
		str.writeObject(settings);
		str.close();
	}

	public void addDefault() {
		setVal("bip", "127.0.0.1");
		setVal("bport", 1336);
		setVal("bid", "Name");
		setVal("bkey", "enckey");
		setVal("bpass", "pass");
		setVal("bcrypt", true);
		setVal("brecat", 10);
		setVal("traynote", true);
		setVal("soundondc", false);
		setVal("soundonc", false);
		setVal("stats", true);
		setVal("jarname", "File");
		setVal("autologin", false);
		setVal("remotescreenstartup", false);
		setVal("askurl", true);
		setVal("max", -1);
		setVal("geoip", true);
		setVal("encryption", true);
		setVal("proxy", false);
		setVal("proxyhost", "127.0.0.1");
		setVal("proxyport", 9050);
		setVal("proxysocks", true);
	}

	@Override
	public File getFile() {
		return new File(Files.getSettings(), ".settings");
	}
}
