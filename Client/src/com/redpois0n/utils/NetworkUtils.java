package com.redpois0n.utils;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

import com.redpois0n.Constants;
import com.redpois0n.ErrorDialog;
import com.redpois0n.net.WebRequest;

public class NetworkUtils {

	public static void openUrl(String str) {
		try {
			Desktop.getDesktop().browse(new URI(str));
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorDialog.create(ex);
		}
	}

	public static String getIP() throws Exception {
		URL url = WebRequest.getUrl(Constants.HOST + "/misc/getip.php");
		InputStream in = url.openStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String ip = reader.readLine();
		reader.close();
		return ip;
	}

}
