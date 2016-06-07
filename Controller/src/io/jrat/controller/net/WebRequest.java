package io.jrat.controller.net;

import io.jrat.controller.Constants;
import io.jrat.controller.exceptions.RequestNotAllowedException;
import io.jrat.controller.settings.Settings;
import io.jrat.controller.utils.Utils;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Map;

public class WebRequest {

	public static URL getUrl(String surl) throws Exception {
		return getUrl(surl, false);
	}

	public static URL getUrl(String turl, boolean ignoreask) throws Exception {
		URL url = null;

		if (ignoreask) {
			return url = new URL(turl);
		}

		if (Settings.getGlobal().getBoolean(Settings.KEY_REQUEST_DIALOG)) {
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

		Map<String, Object> proxySettings = Settings.getGlobal().getProxySettings();
		boolean enabled = (Boolean) proxySettings.get(Settings.KEY_ENABLE_PROXY);

		if (enabled) {
			boolean socks = (Boolean) proxySettings.get(Settings.KEY_PROXY_SOCKS);
			String host = (String) proxySettings.get(Settings.KEY_PROXY_HOST);
			int port = ((Number) proxySettings.get(Settings.KEY_PROXY_PORT)).intValue();

			Proxy proxy = new Proxy(socks ? Proxy.Type.SOCKS : Proxy.Type.HTTP, new InetSocketAddress(host, port));
			connection = (HttpURLConnection) url.openConnection(proxy);
		} else {
			connection = (HttpURLConnection) url.openConnection();
		}

		connection.setReadTimeout(2500);
		connection.connect();

		return connection;
	}
}
