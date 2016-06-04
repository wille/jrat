package io.jrat.common.utils;

import com.redpois0n.oslib.OperatingSystem;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.InputStreamReader;
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


	public static boolean isRoot() throws Exception {
		return OperatingSystem.getOperatingSystem().getType() != OperatingSystem.WINDOWS && new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("whoami").getInputStream())).readLine().equals("root");
	}
}
