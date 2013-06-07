package com.redpois0n.utils;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class ClassUtils {
	
	public static void addToClassPath(File file) throws Exception {
		Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
	    method.setAccessible(true);
	    method.invoke((URLClassLoader) ClassLoader.getSystemClassLoader(), file.toURI().toURL());
	}

}
