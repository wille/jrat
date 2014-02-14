package io.jrat.stub;

import java.util.ArrayList;
import java.util.List;

public class Constants {

	public static final int STATUS_EXECUTED_FILE = 2;
	public static final int STATUS_DOWNLOADING_FILE = 3;
	public static final int STATUS_FAILED_FILE = 4;
	public static final int STATUS_READY = 5;
	public static final int STATUS_DISPLAYED_MSGBOX = 6;
	public static final int STATUS_FAILED_SHUTDOWN = 7;
	public static final int STATUS_STARTING_SHUTDOWN = 8;
	public static final int STATUS_RAN_CMD = 9;
	public static final int STATUS_MKDIR = 10;

	public static final int OS_WIN = 0;
	public static final int OS_MAC = 1;
	public static final int OS_LIN = 2;

	public static final int HTTP_GET = 0;
	public static final int HTTP_POST = 1;
	public static final int HTTP_HEAD = 2;

	public static boolean flooding = false;

	public static final List<String> keys = new ArrayList<String>();
}
