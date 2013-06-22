package pro.jrat.stub.packets.incoming;

import pro.jrat.stub.RemoteShell;

public class Packet24RemoteShellStop extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		if (RemoteShell.p != null) {
			RemoteShell.p.destroy();
		}
	}

}
