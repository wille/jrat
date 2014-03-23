package jrat.api;

public class Connection {

	private int timeout;
	private String pass;
	private String name;

	public Connection(int timeout, String pass, String name) {
		this.timeout = timeout;
		this.pass = pass;
		this.name = name;
	}

	/**
	 * 
	 * @return The timeout in milliseconds
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * 
	 * @return The password
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * 
	 * @return Name of connection
	 */
	public String getName() {
		return name;
	}

}
