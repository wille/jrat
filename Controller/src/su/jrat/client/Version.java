package su.jrat.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;

import su.jrat.client.net.WebRequest;
import su.jrat.client.ui.frames.FrameChangelog;


public class Version {

	public static boolean checkVersion() {
		try {
			BufferedReader dis = new BufferedReader(new InputStreamReader(WebRequest.getInputStream(Constants.HOST + "/api/version.txt")));
			String result = dis.readLine();

			String latest = result;
			if (!result.trim().equals(su.jrat.common.Version.getVersion())) {
				FrameChangelog frame = new FrameChangelog(Constants.CHANGELOG_URL, latest);
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
