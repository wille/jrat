package com.redpois0n.plugins;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import com.redpois0n.Main;
import com.redpois0n.utils.IOUtils;



public class ExternalPlugin {

	public String path;
	public String clazz;
	public String name;

	public ExternalPlugin(String path, boolean load) {
		this.path = path;
		if (load) {
			try {
				JarFile jar = new JarFile(path);
				
				Enumeration<? extends ZipEntry> e = jar.entries();
				while (e.hasMoreElements()) {
					ZipEntry entry = e.nextElement();
					if (entry.getName().equals("info.txt")) {
						clazz = IOUtils.readString(jar.getInputStream(entry));
						break;
					}
				}
				jar.close();

				URLClassLoader loader = new URLClassLoader(new URL[] { new URL("file:" + path) }, Main.class.getClassLoader());
				Class<?> classToLoad = Class.forName(clazz, true, loader);
				Object instance = classToLoad.newInstance();

				name = (String) classToLoad.getMethod("getName", new Class[] { }).invoke(instance, new Object[] { });

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			name = path;
		}
	}
}