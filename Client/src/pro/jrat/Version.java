package pro.jrat;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import pro.jrat.net.WebRequest;
import pro.jrat.ui.frames.FrameChangelog;



public class Version {

	public static boolean checkVersion() {
		try {
			BufferedReader dis = new BufferedReader(new InputStreamReader(WebRequest.getInputStream(Constants.HOST + "/version.txt")));
			String result = dis.readLine();
			String latest = result;
			if (!result.trim().equals(com.redpois0n.common.Version.getVersion())) {
				FrameChangelog frame = new FrameChangelog(Constants.CHANGELOG_URL, latest);
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
