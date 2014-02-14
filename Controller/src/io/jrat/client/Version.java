package io.jrat.client;

import io.jrat.client.net.WebRequest;
import io.jrat.client.ui.frames.FrameChangelog;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;


public class Version {

	public static boolean checkVersion() {
		try {
			BufferedReader dis = new BufferedReader(new InputStreamReader(WebRequest.getInputStream(Constants.HOST + "/version.txt")));
			String result = dis.readLine();

			String latest = result;
			if (!result.trim().equals(io.jrat.common.Version.getVersion())) {
				FrameChangelog frame = new FrameChangelog(Constants.CHANGELOG_URL, latest);
				frame.setVisible(true);
				frame.setTitle("New version! - " + latest);
				if (!Main.trial) {
					JOptionPane.showMessageDialog(null, "When you are premium and upgrading, do not loose your jrat.key file", "Note", JOptionPane.WARNING_MESSAGE);
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
