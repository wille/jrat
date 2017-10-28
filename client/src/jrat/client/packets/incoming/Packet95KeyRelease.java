package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.utils.ScreenUtils;
import jrat.common.utils.Utils;

public class Packet95KeyRelease implements IncomingPacket {

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
