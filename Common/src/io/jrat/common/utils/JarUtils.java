package io.jrat.common.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarUtils {

	public static String getMainClass(JarFile jar) throws Exception {
		String mainClass = null;

		Map<Object, Object> map = jar.getManifest().getMainAttributes();
		for (Object obj : map.keySet()) {
			if (obj.toString().equalsIgnoreCase("main-class")) {
				mainClass = map.get(obj).toString();
				break;
			}
		}
		jar.close();

		return mainClass;

	}

	public static String getMainClassFromInfo(JarFile jar) throws Exception {
		Enumeration<? extends JarEntry> entries = jar.entries();

		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			if (entry.getName().equals("plugin.txt")) {
				return new BufferedReader(new InputStreamReader(jar.getInputStream(entry))).readLine();
			}
		}

		throw new Exception("Main class not found");

	}
}
