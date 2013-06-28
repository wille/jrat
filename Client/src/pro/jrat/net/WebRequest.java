package pro.jrat.net;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import pro.jrat.exceptions.RequestNotAllowedException;
import pro.jrat.settings.Settings;
import pro.jrat.utils.Utils;


public class WebRequest {
	
	private static URL getUrl(String surl) throws Exception {
		System.out.println("Requesting " + surl);
		
		URL url = null;
		
		if (Settings.getGlobal().getBoolean("askurl")) {
			if (Utils.yesNo("HTTP Request", "jRAT tries to connect to:\n\r\n\r" + surl + "\n\r\n\rDo you want to accept it?")) {
				url = new URL(surl);
			} else {
				throw new RequestNotAllowedException(surl);
			}
		} else {
			url = new URL(surl);
		}
		
		return url;
	}
	
	public static URLConnection getConnection(String surl) throws Exception {
		URL url = getUrl(surl);
		
		URLConnection connection = null;
		
		if (Settings.getGlobal().getBoolean("proxy")) {
			Proxy proxy = new Proxy(Settings.getGlobal().getBoolean("proxysocks") ? Proxy.Type.SOCKS : Proxy.Type.HTTP, new InetSocketAddress(Settings.getGlobal().getString("proxyhost"), Settings.getGlobal().getInt("proxyport")));
			connection = url.openConnection(proxy);
		} else {
			connection = url.openConnection();
		}
		
		return connection;
	}
	
	public static InputStream getInputStream(String surl) throws Exception {
		URLConnection connection = getConnection(surl);
		
		return connection.getInputStream();
	}

}
