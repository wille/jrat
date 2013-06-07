package com.redpois0n.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class GeoIP {

	public static final String[] infoArray = { "IP", "Country Code", "Country Name", "Region Code", "Region Name", "City", "Zipcode", "Latitude", "Longitude", "Metro Code", "Area Code" };

	public static String getValue(String val, String ip) {
		String[] info = getInfo(ip);
		
		for (int i = 0; i < info.length; i++) {
			if (infoArray[i].equals(val)) {
				return info[i];
			}
		}
		
		return null;
	}
	
	public static String[] getInfo(String ip) {
		String url = "http://freegeoip.net/json/" + ip;
		String rawData = readLine(url);
		return process(rawData);
	}

	private static String[] process(String s) {
		s = s.replace("{", "").replace("}", "");
		
		String[] indexes = s.split(",");
		String[] data = new String[indexes.length];
		
		for (int i = 0; i < indexes.length; i++) {
			data[i] = indexes[i].split(":")[1].replace("\"", "");
		}
		
		return data;
	}

	private static String readLine(String urlString) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(WebRequest.getInputStream(urlString)));
			return in.readLine();
		} catch (Throwable t) {
			return null;
		}

	}
}
