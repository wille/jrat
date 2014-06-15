package su.jrat.stub.packets.incoming;

import su.jrat.stub.Connection;
import su.jrat.stub.Main;

public class Packet93MouseRelease extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		int x = Connection.readInt();
		int y = Connection.readInt();
		int btn = Connection.readInt();
		int i = Connection.readInt();
		if (i == -1) {
			Main.robot.mouseMove(x, y);
			Main.robot.mouseRelease(btn);
		} else {
			Main.robots[i].mouseMove(x, y);
			Main.robots[i].mouseRelease(btn);
		}
	}

}