package se.jrat.controller.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class IOUtils {

	public static String readString(InputStream is) throws Exception {
		char[] buf = new char[2048];
		Reader r = new InputStreamReader(is, "UTF-8");
		StringBuilder s = new StringBuilder();
		while (true) {
			int n = r.read(buf);
			if (n < 0)
				break;
			s.append(buf, 0, n);
		}
		return s.toString();
	}

	public static void writeFile(File file, byte[] bytes) throws Exception {
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(bytes);
		fos.close();
	}
	
	public static byte[] readFile(File file) throws Exception {
		byte[] array = new byte[(int) file.length()];
		
		FileInputStream fis = new FileInputStream(file);	
		fis.read(array);	
		fis.close();
		
		return array;
	}
}
