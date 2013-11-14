package pro.jrat;

import pro.jrat.net.WebRequest;

public class Constants {

	public static final int MODE_ENV = 0;
	public static final int MODE_PROP = 1;

	public static final int MODE_LINES = 9;
	public static final int MODE_DOTS = 10;

	public static String HOST = "%host%";

	public static final String DOWNLOAD_URL = WebRequest.domains[0] + "/download.php";
	public static final String CHANGELOG_URL = WebRequest.domains[0] + "/changelog.php";

	public static final String NAME = "jRAT";

}
