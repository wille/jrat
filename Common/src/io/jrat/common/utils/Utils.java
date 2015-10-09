package io.jrat.common.utils;

import java.awt.GraphicsEnvironment;
import java.util.Random;

public class Utils {
	
	public static String randomString() {
		return randomString(10);
	}

	public static String randomString(int len) {
		String a = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

		String str = "";

		Random rand = new Random();

		for (int i = 0; i < len; i++) {
			str += a.charAt(rand.nextInt(a.length()));
		}

		return str;
	}

	public static boolean isHeadless() {
		return GraphicsEnvironment.getLocalGraphicsEnvironment().isHeadlessInstance();
	}

}
