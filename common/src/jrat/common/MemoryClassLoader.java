package jrat.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class MemoryClassLoader extends ClassLoader {

	private static final HashMap<String, byte[]> classes = new HashMap<>();

    /**
     * All memory loaded resources
     * Accessible anywhere
     */
    private static final HashMap<String, byte[]> resources = new HashMap<>();

    public MemoryClassLoader(ClassLoader parent) {
        super(parent);
    }

	public MemoryClassLoader(ClassLoader parent, JarInputStream stream) {
		this(parent);

		if (stream != null) {
            this.loadResources(stream);
        }
	}

    @Override
    public InputStream getResourceAsStream(String name) {
        return MemoryClassLoader.getResourceStream(name);
    }

    /**
     * Always returns null since we can't get an URL for a memory resource
     * @return null
     */
	@Override
	public URL getResource(String name) {
		return null;
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
		byte[] data = classes.remove(name);

		if (data != null) {
			return defineClass(name, data, 0, data.length, MemoryClassLoader.class.getProtectionDomain());
		} else {
			throw new ClassNotFoundException(name);
		}
	}

    /**
     * Load all classes and resources from the input stream
     * @param stream
     */
	private void loadResources(JarInputStream stream) {
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
					addClass(entry.getName(), array);
				} else {
					addResource(entry.getName(), array);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof MemoryClassLoader) {
			return ((MemoryClassLoader) o).getParent() == getParent();
		}
		return false;
	}

    /**
     * Returns a resource as a stream
     * @param name
     * @return
     */
    public static InputStream getResourceStream(String name) {
        return new ByteArrayInputStream(getResourceData(name));
    }

    /**
     * Returns the resource byte array
     * @param name
     * @return
     */
    public static byte[] getResourceData(String name) {
        return resources.get(name);
    }

    /**
     * Adds a binary resource
     * @param name
     * @param data
     */
	public static void addResource(String name, byte[] data) {
	    resources.put(name, data);
    }

    public static void addClass(String name, byte[] clazz) {
        if (name.endsWith(".class")) {
            name = getClassName(name);
        }

        classes.put(name, clazz);
    }

    private static String getClassName(String fileName) {
        return fileName.substring(0, fileName.length() - 6).replace('/', '.');
    }
}