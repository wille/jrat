package se.jrat.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import se.jrat.client.net.WebRequest;
import se.jrat.client.ui.frames.FrameChangelog;

public class Version {

	public static boolean checkVersion() {
		try {
			HttpURLConnection uc = WebRequest.getConnection(Constants.HOST + "/api/version.txt");
			BufferedReader dis = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			String result = dis.readLine();
			dis.close();
			uc.disconnect();

			String latest = result;
			if (!result.trim().equals(se.jrat.common.Version.getVersion())) {
				FrameChangelog frame = new FrameChangelog(WebRequest.getUrl(Constants.HOST + "/api/changelog.php"), latest);
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
