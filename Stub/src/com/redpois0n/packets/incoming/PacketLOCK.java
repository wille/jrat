package com.redpois0n.packets.incoming;

public class PacketLOCK extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String operatingSystem = System.getProperty("os.name");
		if (operatingSystem.toLowerCase().contains("windows")) {
			Runtime.getRuntime().exec("rundll32.exe user32.dll, LockWorkStation");
		}
	}

}
