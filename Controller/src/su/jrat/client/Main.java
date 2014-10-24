package su.jrat.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import jrat.api.commands.Commands;
import jrat.api.events.OnDisableEvent;
import su.jrat.client.addons.Plugin;
import su.jrat.client.addons.PluginLoader;
import su.jrat.client.commands.DefaultCommands;
import su.jrat.client.net.WebRequest;
import su.jrat.client.settings.AbstractSettings;
import su.jrat.client.settings.Settings;
import su.jrat.client.settings.Statistics;
import su.jrat.client.settings.Theme;
import su.jrat.client.threads.ThreadCheckVersion;
import su.jrat.client.threads.ThreadPing;
import su.jrat.client.ui.dialogs.DialogEula;
import su.jrat.client.ui.frames.Frame;
import su.jrat.client.utils.TrayIconUtils;
import su.jrat.common.Logger;
import su.jrat.common.OperatingSystem;
import su.jrat.common.Version;

public class Main {

	public static boolean liteVersion = true;
	public static boolean debug;
	public static boolean noSsl;

	public static final List<Slave> connections = new ArrayList<Slave>();
	public static Frame instance;

	public static void main(String[] args) throws Exception {	
		if (argsContains(args, "-locinfo")) {
			System.out.println(System.getProperty("user.dir"));
			System.out.println("jRAT.app: " + new File("jRAT.app/").exists());
			System.out.println("/files/: " + new File("jRAT.app/files/").exists());
		}
		
		if (argsContains(args, "-nossl")) {
			String ssl = WebRequest.domains[0];
			String nossl = WebRequest.domains[1];
			
			WebRequest.domains[1] = ssl;
			WebRequest.domains[0] = nossl;
		}
		
		if (argsContains(args, "-genkey")) {
			Logger.log("Generating key");
			File file = Globals.getKeyFile();
			FileOutputStream out = new FileOutputStream(file);
			out.write(UniqueId.generateBinary());
			out.close();
			Logger.log("Wrote key to " + file.getAbsolutePath());
			System.exit(0);
		}

		debug = argsContains(args, "-debug");

		if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		}
		
		if (!Globals.getFileDirectory().exists()) {
			JOptionPane.showMessageDialog(null, "Could not find /files/, please specify your " + Constants.NAME + " directory", Constants.NAME + "", JOptionPane.WARNING_MESSAGE);
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.showOpenDialog(null);

			File file = chooser.getSelectedFile();

			if (file != null) {
				System.setProperty("user.dir", file.getAbsolutePath());
			}
		}
		
		Globals.mkdirs();

		try {
			Theme.getGlobal().load();
			UIManager.setLookAndFeel(Theme.getGlobal().getTheme());
		} catch (Exception ex) {
			Main.debug("Could not use look and feel, setting default");
			ex.printStackTrace();
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		
		try {
			boolean validated = UniqueId.validate(argsContains(args, "-showhexkey"));

			if (validated) {
				liteVersion = false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			
			if (!argsContains(args, "-debug")) {
				JOptionPane.showMessageDialog(null, Constants.NAME + " is limited, no license detected", Constants.NAME, JOptionPane.ERROR_MESSAGE);
			}
		}

		System.setProperty("jrat.dir", System.getProperty("user.dir"));
		System.setProperty("jrat.version", Version.getVersion());

		try {
			PluginLoader.loadLibs();
			PluginLoader.loadPlugins();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Statistics.getGlobal().load();

		instance = new Frame();

		AbstractSettings.loadAllGlobals();

		boolean showEULA = Settings.getGlobal().getBoolean("showeula");
		if (!showEULA) {
			DialogEula frame = new DialogEula(false);
			frame.setVisible(true);
		}

		if (!argsContains(args, "-noad") && liteVersion) {
			//FrameAd frame = new FrameAd();
			//frame.setVisible(true);
			
			// TODO Ad
		}

		new ThreadCheckVersion().start();
		new ThreadPing().start();

		instance.setVisible(true);

		TrayIconUtils.initialize();
		Sound.initialize();

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				OnDisableEvent event = new OnDisableEvent();
				for (Plugin p : PluginLoader.plugins) {
					try {
						p.getMethods().get(Plugin.ON_DISABLE).invoke(p.getInstance(), new Object[] { event });
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

				AbstractSettings.saveAllGlobals();

				try {
					Theme.getGlobal().save();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}));

		DefaultCommands.addDefault();

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String s;

		while ((s = reader.readLine()) != null) {
			Commands.execute(s, System.out);
		}
	}

	public static boolean argsContains(String[] args, String s) {
		for (String str : args) {
			if (str.equalsIgnoreCase(s)) {
				return true;
			}
		}

		return false;
	}

	public static String getArg(String[] args, String arg) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].equalsIgnoreCase(arg)) {
				return args[i + 1];
			}
		}

		return null;
	}

	public static String debug(Object s) {
		if (s == null) {
			s = "null";
		}
		Logger.log(s.toString());
		return s.toString();
	}

	public static String formatTitle() {
		String suffix = liteVersion && !debug ? "Limited" : "";

		return Constants.NAME + " [" + connections.size() + "] " + Version.getVersion() + " " + suffix;
	}
	
	public static boolean isFeatureEnabled() {
		return Main.debug || !Main.liteVersion;
	}

}
