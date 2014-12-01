package io.jrat.client.net;

import io.jrat.client.Constants;
import io.jrat.client.Globals;
import io.jrat.client.Main;
import io.jrat.client.exceptions.RequestNotAllowedException;
import io.jrat.client.settings.Settings;
import io.jrat.client.utils.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class WebRequest {
	
	public static String workingDomain = null;

	public static String[] domains;
	
	static {
		loadDomains();
	}
	
	public static void loadDomains() {
		try {
			 BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(Globals.getFileDirectory(), "domains.txt"))));
			 
			 String line;
			 List<String> domains = new ArrayList<String>();
			 while ((line = reader.readLine()) != null) {
				 if (line.contains("://jrat.")) {
					 domains.add(line);
				 }
			 }
			 
			 reader.close();
			 
			 WebRequest.domains = domains.toArray(new String[0]);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static URL getUrl(String surl) throws Exception {
		return getUrl(surl, false);
	}

	public static URL getUrl(String surl, boolean ignoreask) throws Exception {
		String turl = surl;
		if (surl.contains(Constants.HOST) && workingDomain == null) {
			for (int i = 0; i < domains.length; i++) {
				try {
					String domain = domains[i];
					URL url = new URL(turl.replace("%host%", domain));
					HttpURLConnection connection = null;
					Main.debug("Checking if " + domain + " is available...");
					if (Settings.getGlobal().getBoolean("proxy")) {
						Proxy proxy = new Proxy(Settings.getGlobal().getBoolean("proxysocks") ? Proxy.Type.SOCKS : Proxy.Type.HTTP, new InetSocketAddress(Settings.getGlobal().getString("proxyhost"), Settings.getGlobal().getInt("proxyport")));
						connection = (HttpURLConnection) url.openConnection(proxy);
					} else {
						connection = (HttpURLConnection) url.openConnection();
					}

					connection.setReadTimeout(2500);

					connection.connect();
					connection.disconnect();
					
					workingDomain = domain;
					break;
				} catch (Exception e) {
					turl = surl.replace(Constants.HOST, domains[i]);
					e.printStackTrace();
					
					if (workingDomain != null) {
						break;
					}
				}
			}
		} 
		
		turl = turl.replace("%host%", workingDomain);

		URL url = null;

		if (ignoreask) {
			return url = new URL(turl);
		}

		if (Settings.getGlobal().getBoolean("askurl")) {
			if (Utils.yesNo("HTTP Request", Constants.NAME + " tries to connect to:\n\r\n\r" + surl + "\n\r\n\rDo you want to accept it?\n\r\n\r(You can turn off this notification in settings)")) {
				url = new URL(surl);
			} else {
				throw new RequestNotAllowedException(surl);
			}
		} else {
			url = new URL(turl);
		}

		return url;
	}

	public static HttpURLConnection getConnection(String surl) throws Exception {
		return getConnection(surl, false);
	}

	public static HttpURLConnection getConnection(String surl, boolean ignoreask) throws Exception {		
		URL url = getUrl(surl);

		HttpURLConnection connection = null;

		if (Settings.getGlobal().getBoolean("proxy")) {
			Proxy proxy = new Proxy(Settings.getGlobal().getBoolean("proxysocks") ? Proxy.Type.SOCKS : Proxy.Type.HTTP, new InetSocketAddress(Settings.getGlobal().getString("proxyhost"), Settings.getGlobal().getInt("proxyport")));
			connection = (HttpURLConnection) url.openConnection(proxy);
		} else {
			connection = (HttpURLConnection) url.openConnection();
		}

		connection.setReadTimeout(2500);
		connection.connect();

		return connection;
	}
}
