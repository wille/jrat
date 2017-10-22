package jrat.common.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import oslib.OperatingSystem;

public class UserUtils {
	
	public static String getHostname() {
		String hostname = null;
				
		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			hostname = System.getenv("COMPUTERNAME");
		} else {
			try {
				Process p = Runtime.getRuntime().exec("hostname");
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
				hostname = reader.readLine();
				reader.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}		
		
		if (hostname == null) {
			hostname = System.getProperty("user.name");
		}
		
		return hostname;
	}

}
