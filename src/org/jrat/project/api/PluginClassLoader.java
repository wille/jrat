package org.jrat.project.api;

import java.io.InputStream;
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
}
