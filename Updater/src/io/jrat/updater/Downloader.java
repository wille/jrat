package io.jrat.updater;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Downloader {

	public static final String URL = "https://jrat.io/downloads/jrat-archive.zip";

	public void update() throws Exception {
		Logger.log("Downloading " + URL);
		URLConnection con = new URL(URL).openConnection();
		InputStream in = con.getInputStream();
		
		ByteArrayOutputStream bais = new ByteArrayOutputStream();		
		
		byte[] byteBuff = new byte[4096];
	    int bytesRead = 0;
	    while ((bytesRead = in.read(byteBuff)) != -1) {
	        bais.write(byteBuff, 0, bytesRead);
	    }

	    in.close();
		bais.close();
		
		ZipInputStream zipStream = new ZipInputStream(new ByteArrayInputStream(bais.toByteArray()));
		ZipEntry entry = null;
		while ((entry = zipStream.getNextEntry()) != null) {	
		    File file = new File(Utils.getJarFile().getParentFile().getParentFile(), entry.getName());
		    
			Logger.log("Extracting " + entry.getName() + " to " + file.getAbsolutePath());
		    
		    if (entry.isDirectory()) {
		    	file.mkdir();
		    	continue;
		    }
		    
		    FileOutputStream out = new FileOutputStream(file);

		    byteBuff = new byte[4096];
		    bytesRead = 0;
		    while ((bytesRead = zipStream.read(byteBuff)) != -1) {
		        out.write(byteBuff, 0, bytesRead);
		    }

		    out.close();
		    zipStream.closeEntry();
		}
		zipStream.close(); 
	}

}
