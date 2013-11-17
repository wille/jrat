package pro.jrat.stub;

public class DnsRotator {
	
	private static int currentIndex = 0;
	
	public static Address getNextAddress() {
		if (currentIndex + 1 >= Main.addresses.length) {
			currentIndex = 0;
		}
		
		String[] rawAddr = Main.addresses[currentIndex++].split(":");
		
		Address address = new Address(rawAddr[0], Integer.parseInt(rawAddr[1]));
		
		return address;
	}

}
