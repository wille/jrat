package se.jrat.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import se.jrat.common.Version;
import se.jrat.controller.net.WebRequest;

public class VersionChecker {
	
	private String latest;

	public boolean isUpToDate() {
		try {
			HttpURLConnection uc = WebRequest.getConnection(Constants.HOST + "/api/version.txt");
			BufferedReader dis = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			latest = dis.readLine();
			dis.close();
			uc.disconnect();
				
			return latest.equals(Version.getVersion());
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public String getLatest() {
		return latest;
	}

}
