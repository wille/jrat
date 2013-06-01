package com.redpois0n.net;

import java.net.URL;

import com.redpois0n.Settings;
import com.redpois0n.exceptions.RequestNotAllowedException;
import com.redpois0n.util.Util;

public class WebRequest {
	
	public static URL getUrl(String url) throws Exception {
		System.out.println("Requesting " + url);
		if (Settings.getBoolean("askurl")) {
			if (Util.yesNo("HTTP Request", "jRAT tries to connect to:\n\r\n\r" + url + "\n\r\n\rDo you want to accept it?")) {
				return new URL(url);
			} else {
				System.out.println("Was not allowed to request: " + url);
				throw new RequestNotAllowedException(url);
			}
		} else {
			return new URL(url);
		}
	}

}
