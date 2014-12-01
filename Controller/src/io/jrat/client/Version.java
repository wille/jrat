package io.jrat.client;

import io.jrat.client.net.WebRequest;
import io.jrat.client.ui.frames.FrameChangelog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import javax.swing.JOptionPane;


public class Version {

	public static boolean checkVersion() {
		try {
			HttpURLConnection uc = WebRequest.getConnection(Constants.HOST + "/api/version.txt");
			BufferedReader dis = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			String result = dis.readLine();
			dis.close();
			uc.disconnect();

			String latest = result;
			if (!result.trim().equals(io.jrat.common.Version.getVersion())) {
				FrameChangelog frame = new FrameChangelog(WebRequest.getUrl(Constants.HOST + "/api/changelog.php"), latest);
				frame.setVisible(true);
				frame.setTitle("New version! - " + latest);
				if (!Main.liteVersion) {
					JOptionPane.showMessageDialog(null, "When you are premium and upgrading, do not loose your jrat.key file\r\nAll your settings will be removed", "Note", JOptionPane.WARNING_MESSAGE);
				}
				return true;
			}
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

}
