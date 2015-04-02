package se.jrat.client.build;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.jar.JarFile;

import jrat.api.utils.IOUtils;
import jrat.api.utils.JarUtils;
import se.jrat.client.Main;
import se.jrat.client.ui.frames.FrameAppInfo;

public class BuildApp {

	public static void build(String input, String output, FrameAppInfo frame) throws Exception {
		build(new File(input), new File(output), frame.getIconFile(), JarUtils.getMainClass(new JarFile(input)), frame.getAppTitle(), false, 6);
	}

	public static final String FILE_INFO = "/files/osx/app.plist";
	public static final String FILE_LAUNCHER = "/files/osx/launcher.sh";

	public static void build(File input, File output, File icon, String mainclass, String appTitle, boolean dockIcon, int minimumVersion) throws Exception {
		File basePath = new File(output.getAbsolutePath(), "/Contents");

		new File(basePath, "/MacOS").mkdirs();
		new File(basePath, "/Resources").mkdirs();

		File info = new File(basePath, "/Info.plist");
		File launcher = new File(basePath, "/MacOS/launcher");
		File jar = new File(basePath, "/MacOS/" + input.getName());

		File iconFile = new File(basePath + "/Resources/application.icns");

		launcher.createNewFile();

		if (icon != null && icon.exists()) {
			copyFile(icon, iconFile);
		}

		copyLauncher(launcher, input.getName(), appTitle, dockIcon, minimumVersion);

		IOUtils.copy(Main.class.getResourceAsStream(FILE_INFO), new FileOutputStream(info));
		copyFile(input, jar);
	}

	public static void copyFile(File input, File output) throws Exception {
		if (!output.getParentFile().exists()) {
			output.getParentFile().mkdirs();
		}

		IOUtils.copy(new FileInputStream(input), new FileOutputStream(output));
	}
	
	public static void copyLauncher(File launcherDest, String jarName, String appTitle, boolean showIcon, int minimumVersion) throws Exception {
		InputStream is = Main.class.getResourceAsStream(FILE_LAUNCHER);

		FileWriter dest = new FileWriter(launcherDest.getAbsolutePath());

		BufferedReader br = null;
		PrintWriter pw = null;

		br = new BufferedReader(new InputStreamReader(is));
		pw = new PrintWriter(dest);
		
		StringBuilder sb = new StringBuilder();

		String line;
		
		while ((line = br.readLine()) != null) {
			sb.append(line + "\n");
		}
		
		br.close();
		
		String rawFile = sb.toString();
		rawFile = rawFile.replace("%NAME%", "Java Application").replace("%MINOR%", minimumVersion + "").replace("%JAR%", jarName).replace("%DOCK%", showIcon + "").replace("%NAME%", appTitle);
		
		pw.print(rawFile); 
		
		pw.close();
	}
}

