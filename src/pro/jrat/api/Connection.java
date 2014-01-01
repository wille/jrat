package pro.jrat.api;

public class Connection {

	private int timeout;
	private String key;
	private String pass;
	private String name;

	public Connection(int timeout, String key, String pass, String name) {
		this.timeout = timeout;
		this.key = key;
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
	 * @return The encryption key
	 */
	public String getKey() {
		return key;
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
