package su.jrat.client.utils;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import su.jrat.client.Constants;
import su.jrat.client.ErrorDialog;
import su.jrat.client.net.WebRequest;


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
		BufferedReader reader = new BufferedReader(new InputStreamReader(WebRequest.getInputStream(Constants.HOST + "/getip.php")));
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

}
