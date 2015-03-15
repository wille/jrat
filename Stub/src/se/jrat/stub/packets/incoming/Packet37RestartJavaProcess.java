package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.utils.Utils;

import com.redpois0n.oslib.OperatingSystem;

public class Packet37RestartJavaProcess extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		try {

			Connection.socket.close();
		} catch (Exception ex) {
		}
		String javapath = System.getProperty("java.home");

		String path = Utils.getJarFile().getAbsolutePath();

		if (path.startsWith("/") && OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			path = path.substring(1, path.length());
		}

		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			Runtime.getRuntime().exec(new String[] { javapath + "\\bin\\javaw.exe", "-jar", path });
		} else {
			Runtime.getRuntime().exec(new String[] { "java", "-jar", path });
		}
		System.exit(0);
	}

}
