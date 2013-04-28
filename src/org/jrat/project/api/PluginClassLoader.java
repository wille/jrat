package org.jrat.project.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class PluginClassLoader extends ClassLoader {

	public PluginClassLoader(ClassLoader parent) {
		super(parent);
	}
	
	@Override
	public InputStream getResourceAsStream(String resource) {
		return null;
	}
	
	@Override
	public URL getResource(String resource) {
		return null;
	}
	
	/**
	 * 
	 * @param folder
	 * @param resource
	 * @return FileInputStream from file in plugin folder
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public InputStream getPluginFile(String folder, String resource) throws FileNotFoundException, IOException {
		return new FileInputStream(new File("plugins/" + folder, resource));
	}
	
	/**
	 * 
	 * @param folder
	 * @param resource
	 * @return URL to file in plugin folder
	 * @throws MalformedURLException
	 */
	public URL getPluginUrl(String folder, String resource) throws MalformedURLException {
		return new URL("plugins/" + folder + "/" + resource);
	}
}
