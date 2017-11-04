package jrat.controller;

import jrat.common.Version;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class VersionChecker {
	
	private String latest;

	public boolean isUpToDate() {
		try {
			URLConnection uc = new URL(Constants.HOST + "/api/version.txt").openConnection();
			uc.connect();

			BufferedReader dis = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			latest = dis.readLine();
			dis.close();

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
