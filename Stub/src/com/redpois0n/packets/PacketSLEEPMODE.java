package com.redpois0n.packets;

public class PacketSLEEPMODE extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		String operatingSystem = System.getProperty("os.name");
		if (operatingSystem.toLowerCase().contains("windows")) {
			Runtime.getRuntime().exec("shutdown.exe -h");
		}
	}

}
