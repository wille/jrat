package jrat.client.packets.incoming;

import jrat.client.Connection;
import oslib.OperatingSystem;

import java.io.File;
import java.io.FileWriter;


public class Packet76Speech implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		final String speech = con.readLine();
		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
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
		} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.MACOS) {
			try {
				Runtime.getRuntime().exec("say " + speech);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
