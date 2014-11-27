package io.jrat.client.net;

import java.net.MalformedURLException;
import java.net.URL;

public class URLParser {

	public static void parse(String url) throws MalformedURLException {
		new URL(url);
	}

}
