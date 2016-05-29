package io.jrat.controller.net;

import io.jrat.controller.Constants;
import io.jrat.controller.exceptions.RequestNotAllowedException;
import io.jrat.controller.settings.Settings;
import io.jrat.controller.utils.Utils;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

public class WebRequest {

	public static String workingDomain = null;

	public static URL getUrl(String surl) throws Exception {
		return getUrl(surl, false);
	}

	public static URL getUrl(String turl, boolean ignoreask) throws Exception {
		URL url = null;

		if (ignoreask) {
			return url = new URL(turl);
		}

		if (Settings.getGlobal().getBoolean("askurl")) {
			if (Utils.yesNo("HTTP Request", Constants.NAME + " tries to connect to:\n\r\n\r" + turl
					+ "\n\r\n\rDo you want to accept it?\n\r\n\r(You can turn off this notification in settings)")) {
				url = new URL(turl);
			} else {
				throw new RequestNotAllowedException(turl);
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
		URL url = getUrl(surl, ignoreask);

		HttpURLConnection connection = null;

		if (Settings.getGlobal().getBoolean("proxy")) {
			Proxy proxy = new Proxy(Settings.getGlobal().getBoolean("proxysocks") ? Proxy.Type.SOCKS : Proxy.Type.HTTP, new InetSocketAddress(
					Settings.getGlobal().getString("proxyhost"), Settings.getGlobal().getInt("proxyport")));
			connection = (HttpURLConnection) url.openConnection(proxy);
		} else {
			connection = (HttpURLConnection) url.openConnection();
		}

		connection.setReadTimeout(2500);
		connection.connect();

		return connection;
	}
}
