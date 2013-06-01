package com.redpois0n;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.redpois0n.net.WebRequest;
import com.redpois0n.ui.frames.FrameChangelog;


public class Version {

	public static boolean checkVersion() {
		try {
			URL url = WebRequest.getUrl(Constants.HOST + "/version.txt");
			URLConnection con = url.openConnection();
			con.connect();
			InputStream is = con.getInputStream();
			BufferedReader dis = new BufferedReader(new InputStreamReader(is));
			String result = dis.readLine();
			String latest = result;
			if (!result.trim().equals(com.redpois0n.common.Version.getVersion())) {
				FrameChangelog frame = new FrameChangelog(Constants.PARSE_CHANGELOG_URL.replace("%VERSION%", latest), latest);
				frame.setVisible(true);
				frame.setTitle("New version! - " + latest);
				return true;
			}
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

}
