package pro.jrat.stub;

public class Address {
	
	private final String address;
	private final int port;
	
	public Address(String address, int port) {
		this.address = address;
		this.port = port;
	}

	public String getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

}
