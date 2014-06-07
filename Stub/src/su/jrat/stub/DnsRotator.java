package su.jrat.stub;

public class DnsRotator {

	private static int currentIndex = 0;

	public static Address getNextAddress() {
		if (currentIndex + 1 >= Configuration.addresses.length) {
			currentIndex = 0;
		}

		String[] rawAddr = Configuration.addresses[currentIndex++].split(":");

		Address address = new Address(rawAddr[0], Integer.parseInt(rawAddr[1]));

		return address;
	}

}
