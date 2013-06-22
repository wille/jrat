package pro.jrat.stub.packets.incoming;

import pro.jrat.stub.Connection;
import pro.jrat.stub.packets.outgoing.Packet34CustomDirectory;

import com.redpois0n.common.OperatingSystem;

public class Packet41SpecialDirectory extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String location = Connection.readLine();

		String ret = null;

		if (location.equals("DESKTOP")) {
			ret = System.getProperty("user.home") + "/Desktop/";
		} else if (location.equals("APPDATA")) {
			if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
				ret = System.getenv("APPDATA");
			} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
				ret = System.getProperty("user.home") + "Library/Application Support/";
			} else {
				ret = System.getProperty("java.io.tmpdir");
			}
		} else if (location.equals("TEMP")) {
			ret = System.getProperty("java.io.tmpdir");
		}

		if (ret != null) {
			Connection.addToSendQueue(new Packet34CustomDirectory(ret));
		}
	}

}
